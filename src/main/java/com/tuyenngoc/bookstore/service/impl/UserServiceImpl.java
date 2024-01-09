package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.domain.dto.UserDto;
import com.tuyenngoc.bookstore.domain.entity.User;
import com.tuyenngoc.bookstore.domain.mapper.UserMapper;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.UserRepository;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.UserService;
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
