package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.annotation.CurrentUser;
import com.bookstore.bookstore.annotation.RestApiV1;
import com.bookstore.bookstore.base.VsResponseUtil;
import com.bookstore.bookstore.constant.UrlConstant;
import com.bookstore.bookstore.security.CustomUserDetails;
import com.bookstore.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiV1
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize(value = "hasRole('ADMIN')")
    @Tags({@Tag(name = "user-controller-admin"), @Tag(name = "user-controller")})
    @Operation(summary = "API get current user login")
    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getCurrentUser(@Parameter(name = "userDetails", hidden = true)
                                            @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(userService.getCurrentUser(userDetails));
    }
}
