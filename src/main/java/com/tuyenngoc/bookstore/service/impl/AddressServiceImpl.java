package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.domain.entity.Address;
import com.tuyenngoc.bookstore.repository.AddressRepository;
import com.tuyenngoc.bookstore.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address createAddress(String addressName) {
        Address address = new Address();
        address.setAddressName(addressName);
        return addressRepository.save(address);
    }
}
