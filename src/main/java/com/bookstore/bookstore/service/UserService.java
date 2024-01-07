package com.bookstore.bookstore.service;

import com.bookstore.bookstore.domain.dto.response.UserDto;
import com.bookstore.bookstore.security.CustomUserDetails;

public interface UserService {

    UserDto getCurrentUser(CustomUserDetails userDetails);
}
