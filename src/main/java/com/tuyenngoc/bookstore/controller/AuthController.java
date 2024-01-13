package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.AddressDto;
import com.tuyenngoc.bookstore.domain.dto.request.*;
import com.tuyenngoc.bookstore.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "API Login")
    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        return VsResponseUtil.success(authService.login(request));
    }

    @Operation(summary = "API Logout")
    @PostMapping(UrlConstant.Auth.LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return VsResponseUtil.success(authService.logout(request, response, authentication));
    }

    @Operation(summary = "API Refresh token")
    @PostMapping(UrlConstant.Auth.REFRESH_TOKEN)
    public ResponseEntity<?> refresh(@Valid @RequestBody TokenRefreshRequestDto tokenRefreshRequestDto) {
        return VsResponseUtil.success(authService.refresh(tokenRefreshRequestDto));
    }

    @Operation(summary = "API Register")
    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto requestDto, @RequestParam float latitude, @RequestParam float longitude) {
        return VsResponseUtil.success(authService.register(requestDto, new AddressDto(latitude, longitude)));
    }

    @Operation(summary = "API forget password")
    @PostMapping(UrlConstant.Auth.FORGET_PASSWORD)
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequestDto requestDto) {
        return VsResponseUtil.success(authService.forgetPassword(requestDto));
    }

    @Operation(summary = "API change password")
    @PatchMapping(UrlConstant.Auth.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequestDto requestDto, @PathVariable String username) {
        return VsResponseUtil.success(authService.changePassword(requestDto, username));
    }

}
