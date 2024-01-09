package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer() {
        return null;
    }

}
