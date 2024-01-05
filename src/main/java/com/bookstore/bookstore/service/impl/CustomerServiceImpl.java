package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.domain.entity.Customer;
import com.bookstore.bookstore.repository.CustomerRepository;
import com.bookstore.bookstore.service.CustomerService;
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
