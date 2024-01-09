package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.UserDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;

public interface UserService {

    UserDto getCurrentUser(CustomUserDetails userDetails);
}
