package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestApiV1
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "API get favorite products by customer id")
    @GetMapping(UrlConstant.Customer.GET_FAVORITE_PRODUCTS)
    public ResponseEntity<?> getFavoriteProducts(@PathVariable int customerId, @ParameterObject @Valid PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(customerService.getFavoriteProducts(customerId, requestDto));
    }

    @Operation(summary = "API check favorite product in customer")
    @GetMapping(UrlConstant.Customer.CHECK_FAVORITE_PRODUCT)
    public ResponseEntity<?> checkFavoriteProduct(@PathVariable int customerId, @PathVariable int productId) {
        return VsResponseUtil.success(customerService.checkFavoriteProduct(customerId, productId));
    }

    @Operation(summary = "API add favorite products")
    @PostMapping(UrlConstant.Customer.ADD_FAVORITE_PRODUCT)
    public ResponseEntity<?> addFavoriteProduct(@PathVariable int customerId, @PathVariable int productId) {
        return VsResponseUtil.success(customerService.addFavoriteProduct(customerId, productId));
    }

    @Operation(summary = "API delete favorite products")
    @DeleteMapping(UrlConstant.Customer.REMOVE_FAVORITE_PRODUCT)
    public ResponseEntity<?> removeFavoriteProduct(@PathVariable int customerId, @PathVariable int productId) {
        return VsResponseUtil.success(customerService.removeFavoriteProduct(customerId, productId));
    }
}
