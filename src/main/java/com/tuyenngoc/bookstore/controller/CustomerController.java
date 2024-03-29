package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.CreateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.UpdateCustomerRequestDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Customer")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "API get favorite products")
    @GetMapping(UrlConstant.Customer.GET_FAVORITE_PRODUCTS)
    public ResponseEntity<?> getFavoriteProducts(
            @ParameterObject @Valid PaginationFullRequestDto requestDto,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(customerService.getFavoriteProducts(userDetails.getCustomerId(), requestDto));
    }

    @Operation(summary = "API check favorite product")
    @GetMapping(UrlConstant.Customer.CHECK_FAVORITE_PRODUCT)
    public ResponseEntity<?> checkFavoriteProduct(
            @PathVariable int productId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(customerService.checkFavoriteProduct(userDetails.getCustomerId(), productId));
    }

    @Operation(summary = "API add favorite products")
    @PostMapping(UrlConstant.Customer.ADD_FAVORITE_PRODUCT)
    public ResponseEntity<?> addFavoriteProduct(
            @PathVariable int productId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(customerService.addFavoriteProduct(userDetails.getCustomerId(), productId));
    }

    @Operation(summary = "API delete favorite products")
    @DeleteMapping(UrlConstant.Customer.REMOVE_FAVORITE_PRODUCT)
    public ResponseEntity<?> removeFavoriteProduct(
            @PathVariable int productId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(customerService.removeFavoriteProduct(userDetails.getCustomerId(), productId));
    }

    @Operation(summary = "API update customer")
    @PutMapping(UrlConstant.Customer.UPDATE_CUSTOMER)
    public ResponseEntity<?> updateCustomer(
            @Valid @RequestBody UpdateCustomerRequestDto updateCustomerRequestDto,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(customerService.updateCustomer(userDetails.getCustomerId(), updateCustomerRequestDto));
    }

    @Operation(summary = "API upload avatar")
    @PostMapping(value = UrlConstant.Customer.UPLOAD_AVATAR, consumes = "multipart/form-data")
    public ResponseEntity<?> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(customerService.uploadAvatar(userDetails.getCustomerId(), file));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API upload images")
    @PostMapping(value = UrlConstant.Customer.UPLOAD_IMAGES, consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImages(
            @RequestParam("files") List<MultipartFile> files,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(customerService.uploadImages(userDetails.getUsername(), files));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get todo")
    @GetMapping(UrlConstant.Customer.GET_TODO)
    public ResponseEntity<?> getTodo(@CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(customerService.getTodo(userDetails.getCustomerId()));
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @Operation(summary = "API get count customer")
    @GetMapping(UrlConstant.Customer.GET_COUNT_CUSTOMER)
    public ResponseEntity<?> getCountCustomer() {
        return VsResponseUtil.success(customerService.getCountCustomer());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API get customers")
    @GetMapping(UrlConstant.Customer.GET_CUSTOMERS)
    public ResponseEntity<?> getCustomers(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(customerService.getCustomers(requestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API get customer")
    @GetMapping(UrlConstant.Customer.GET_CUSTOMER)
    public ResponseEntity<?> getCustomer(@PathVariable int customerId) {
        return VsResponseUtil.success(customerService.getCustomer(customerId));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "API create customer")
    @PostMapping(UrlConstant.Customer.CREATE_CUSTOMER)
    public ResponseEntity<?> getCustomer(@Valid @RequestBody CreateCustomerRequestDto requestDto) {
        return VsResponseUtil.success(customerService.createCustomer(requestDto));
    }
}
