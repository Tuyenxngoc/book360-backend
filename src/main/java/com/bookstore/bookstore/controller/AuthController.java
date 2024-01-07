package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.annotation.RestApiV1;
import com.bookstore.bookstore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;


@RequiredArgsConstructor
@Validated
@RestApiV1
public class AuthController {

    private final AuthService authService;

}
