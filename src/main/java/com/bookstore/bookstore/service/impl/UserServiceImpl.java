package com.bookstore.bookstore.service.impl;

import com.bookstore.bookstore.domain.dto.response.UserDto;
import com.bookstore.bookstore.domain.entity.User;
import com.bookstore.bookstore.domain.mapper.UserMapper;
import com.bookstore.bookstore.exception.NotFoundException;
import com.bookstore.bookstore.repository.UserRepository;
import com.bookstore.bookstore.security.CustomUserDetails;
import com.bookstore.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto getCurrentUser(CustomUserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException(""));
        return userMapper.toUserDto(user);
    }
}
