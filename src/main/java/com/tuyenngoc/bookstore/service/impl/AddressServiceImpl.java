package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.repository.AddressRepository;
import com.tuyenngoc.bookstore.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

}
