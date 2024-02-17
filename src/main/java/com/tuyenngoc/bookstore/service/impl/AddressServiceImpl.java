package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.CommonConstant;
import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.request.CreateAddressRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.address.GetAddressResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Address;
import com.tuyenngoc.bookstore.domain.entity.AddressDetail;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.mapper.AddressDetailMapper;
import com.tuyenngoc.bookstore.domain.mapper.AddressMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.AddressDetailRepository;
import com.tuyenngoc.bookstore.repository.AddressRepository;
import com.tuyenngoc.bookstore.service.AddressService;
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
    public CommonResponseDto saveLocationCustomer(int customerId, CreateAddressRequestDto requestDto) {
        AddressDetail addressDetail;
        if (requestDto.getAddressId() == null) {//create address detail
            // check address count
            int addressCount = addressDetailRepository.countByCustomerId(customerId);
            if (addressCount >= CommonConstant.MAX_ADDRESS_LIMIT) {
                Object[] args = {CommonConstant.MAX_ADDRESS_LIMIT};
                String errorMessage = messageSource.getMessage(ErrorMessage.Address.TOO_MANY_ADDRESSES, args, LocaleContextHolder.getLocale());
                return new CommonResponseDto(errorMessage);
            }
            addressDetail = addressDetailMapper.toAddressDetail(requestDto);
            // set default address if address count is 0
            if (addressCount == 0) {
                addressDetail.setDefaultAddress(true);
            } else if (addressDetail.isDefaultAddress()) {
                addressDetailRepository.resetDeFaultAddress(customerId);
            }
        } else {// update address detail
            //get address detail
            addressDetail = addressDetailRepository.findByCustomerIdAndId(customerId, requestDto.getAddressId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Address.ERR_NOT_FOUND_ID, String.valueOf(requestDto.getAddressId())));
            //Set new values
            addressDetail.setFullName(requestDto.getFullName());
            addressDetail.setPhoneNumber(requestDto.getPhoneNumber());
            addressDetail.setType(requestDto.getType());
            // set the default address if the current address is not the default address
            if (!addressDetail.isDefaultAddress() && requestDto.getDefaultAddress()) {
                addressDetailRepository.resetDeFaultAddress(customerId);
                addressDetailRepository.setDeFaultAddress(customerId, requestDto.getAddressId());
            }
        }

        Customer customer = new Customer();
        customer.setId(customerId);

        Address address = addressRepository.findByAddressName(requestDto.getAddressName())
                .orElse(addressMapper.toAddress(requestDto));

        addressDetail.setCustomer(customer);
        addressDetail.setAddress(address);
        addressDetailRepository.save(addressDetail);

        String message = messageSource.getMessage(SuccessMessage.CREATE, null, LocaleContextHolder.getLocale());
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
    public GetAddressResponseDto getAddressDetail(int customerId, int addressDetailId) {
        return addressDetailRepository.getAddressDetailByCustomerIdAndId(customerId, addressDetailId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Address.ERR_NOT_FOUND_ID, String.valueOf(addressDetailId)));
    }

    @Override
    public List<GetAddressResponseDto> getAddressDetails(int customerId) {
        return addressDetailRepository.findAllByCustomerId(customerId);
    }

}