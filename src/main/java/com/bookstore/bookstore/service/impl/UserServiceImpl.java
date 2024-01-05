package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.repository.UserRepository;
import com.bookstore.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
}
