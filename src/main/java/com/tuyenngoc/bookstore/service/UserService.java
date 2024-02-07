package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.UserDto;

public interface UserService {

    UserDto getCurrentUser(String username);

}
