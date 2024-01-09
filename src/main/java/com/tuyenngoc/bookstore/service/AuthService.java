package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.LoginRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.LoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto request);

    CommonResponseDto logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
}
