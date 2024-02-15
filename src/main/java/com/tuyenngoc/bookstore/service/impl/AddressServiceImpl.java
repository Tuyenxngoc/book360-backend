package com.tuyenngoc.bookstore.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.request.CreateAddressRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Address;
import com.tuyenngoc.bookstore.domain.entity.AddressDetail;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.mapper.AddressDetailMapper;
import com.tuyenngoc.bookstore.domain.mapper.AddressMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.AddressDetailRepository;
import com.tuyenngoc.bookstore.repository.AddressRepository;
import com.tuyenngoc.bookstore.service.AddressService;
import com.tuyenngoc.bookstore.util.AddressUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final AddressDetailRepository addressDetailRepository;

    private final MessageSource messageSource;

    private final AddressMapper addressMapper;

    private final AddressDetailMapper addressDetailMapper;

    @Override
    public void getAddressName(Address address) {
        try {
            String json = AddressUtil.getLocationName(address.getLatitude(), address.getLongitude());
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(json);

            JsonNode rootAddress = rootNode.get("address");

            String county = rootAddress.get("county").asText();
            String state = rootAddress.get("state").asText();
            String country = rootAddress.get("country").asText();

            String fullAddress = rootNode.get("display_name").asText();

            address.setCountry(country);
            address.setState(state);
            address.setCity(county);

            address.setFullAddress(fullAddress);

            address.setValid(true);
        } catch (JsonProcessingException e) {
            log.error("Error processing {}", e.getMessage(), e);
        }
    }

    @Override
    public CommonResponseDto saveLocationCustomer(int customerId, CreateAddressRequestDto requestDto) {
        if (addressDetailRepository.countByCustomerId(customerId) > 5) {
            String errorMessage = messageSource.getMessage(ErrorMessage.Address.TOO_MANY_ADDRESSES, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(errorMessage);
        }
        AddressDetail addressDetail;
        if (requestDto.getAddressId() == null) {//create address detail
            addressDetail = addressDetailMapper.toAddressDetail(requestDto);

            Address address = addressRepository.findByLatitudeAndLongitude(requestDto.getLatitude(), requestDto.getLongitude())
                    .orElse(addressMapper.toAddress(requestDto));

            addressDetail.setAddress(address);
        } else {// update address detail
            addressDetail = addressDetailRepository.findByCustomerIdAndId(customerId, requestDto.getAddressId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Address.ERR_NOT_FOUND_ID, String.valueOf(requestDto.getAddressId())));
            Address address = addressDetail.getAddress();

            //Set new values
            addressDetail.setFullName(requestDto.getFullName());
            addressDetail.setPhoneNumber(requestDto.getPhoneNumber());
            addressDetail.setType(requestDto.getType());
        }

        Customer customer = new Customer();
        customer.setId(customerId);

        addressDetail.setCustomer(customer);
        addressDetailRepository.save(addressDetail);

        String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
        return new CommonResponseDto(message);
    }

    @Override
    public CommonResponseDto setDefaultAddress(int customerId, int addressDetailId) {
        Boolean isDefaultAddress = addressDetailRepository.getDefaultAddress(customerId, addressDetailId);

        if (isDefaultAddress == null) {
            throw new NotFoundException(ErrorMessage.Address.ERR_NOT_FOUND_ID, String.valueOf(addressDetailId));
        }

        if (!isDefaultAddress) {
            addressDetailRepository.resetDeFaultAddress(customerId);
            addressDetailRepository.setDeFaultAddress(customerId, addressDetailId);

            String message = messageSource.getMessage(SuccessMessage.UPDATE, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(message);
        } else {
            String message = messageSource.getMessage(ErrorMessage.Address.NO_NEED_TO_UPDATE, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(message);
        }
    }

    @Override
    public CommonResponseDto deleteAddressDetail(int customerId, int addressDetailId) {
        Boolean isDefaultAddress = addressDetailRepository.getDefaultAddress(customerId, addressDetailId);

        if (isDefaultAddress == null) {
            throw new NotFoundException(ErrorMessage.Address.ERR_NOT_FOUND_ID, String.valueOf(addressDetailId));
        }

        if (isDefaultAddress) {
            String errorMessage = messageSource.getMessage(ErrorMessage.Address.CANNOT_DELETE_DEFAULT_ADDRESS, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(errorMessage);
        } else {
            addressDetailRepository.deleteByCustomerIdAndId(customerId, addressDetailId);
            String successMessage = messageSource.getMessage(SuccessMessage.DELETE, null, LocaleContextHolder.getLocale());
            return new CommonResponseDto(successMessage);
        }
    }

    @Override
    public AddressDetail getAddressDetail(int customerId, int addressDetailId) {
        return addressDetailRepository.findByCustomerIdAndId(customerId, addressDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Address.ERR_NOT_FOUND_ID, String.valueOf(addressDetailId)));
    }

    @Override
    public List<AddressDetail> getAddressDetails(int customerId) {
        return addressDetailRepository.findAllByCustomerId(customerId);
    }
}