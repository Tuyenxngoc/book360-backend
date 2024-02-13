package com.tuyenngoc.bookstore.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuyenngoc.bookstore.domain.entity.Address;
import com.tuyenngoc.bookstore.repository.AddressRepository;
import com.tuyenngoc.bookstore.service.AddressService;
import com.tuyenngoc.bookstore.util.AddressUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public void getAddressName(Address address) {
        try {
            String json = AddressUtil.getLocationName(address.getLatitude(), address.getLongitude());
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(json);

            String county = rootNode.get("address").get("county").asText();
            String state = rootNode.get("address").get("state").asText();
            String country = rootNode.get("address").get("country").asText();

            address.setCountry(country);
            address.setState(state);
            address.setCity(county);
            address.setValid(true);
        } catch (JsonProcessingException e) {
            log.error("Error processing {}", e.getMessage(), e);
        }
    }
}