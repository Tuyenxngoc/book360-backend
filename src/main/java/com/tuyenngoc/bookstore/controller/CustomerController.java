package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.CustomerDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "API get favorite products by customer id")
    @GetMapping(UrlConstant.Customer.GET_FAVORITE_PRODUCTS)
    public ResponseEntity<?> getFavoriteProducts(@ParameterObject @Valid PaginationFullRequestDto requestDto,
                                                 @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.getFavoriteProducts(userDetails.getCustomerId(), requestDto));
    }

    @Operation(summary = "API check favorite product in customer")
    @GetMapping(UrlConstant.Customer.CHECK_FAVORITE_PRODUCT)
    public ResponseEntity<?> checkFavoriteProduct(@PathVariable int productId,
                                                  @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.checkFavoriteProduct(userDetails.getCustomerId(), productId));
    }

    @Operation(summary = "API add favorite products")
    @PostMapping(UrlConstant.Customer.ADD_FAVORITE_PRODUCT)
    public ResponseEntity<?> addFavoriteProduct(@PathVariable int productId,
                                                @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.addFavoriteProduct(userDetails.getCustomerId(), productId));
    }

    @Operation(summary = "API delete favorite products")
    @DeleteMapping(UrlConstant.Customer.REMOVE_FAVORITE_PRODUCT)
    public ResponseEntity<?> removeFavoriteProduct(@PathVariable int productId,
                                                   @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.removeFavoriteProduct(userDetails.getCustomerId(), productId));
    }

    @Operation(summary = "API upload image")
    @PostMapping(value = UrlConstant.Customer.UPLOAD_IMAGE, consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.uploadImage(userDetails.getUsername(), file));
    }

    @Operation(summary = "API update customer")
    @PutMapping(value = UrlConstant.Customer.UPDATE_CUSTOMER)
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDto customerDto,
                                            @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.updateCustomer(userDetails.getCustomerId(), customerDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API get todo for admin")
    @GetMapping(UrlConstant.Customer.GET_TODO)
    public ResponseEntity<?> getTodo(@CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.getTodo(userDetails.getCustomerId()));
    }
}
