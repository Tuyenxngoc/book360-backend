package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.repository.AddressRepository;
import com.bookstore.bookstore.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
}
