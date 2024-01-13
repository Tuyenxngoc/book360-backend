package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.request.AddProductToCartResponseDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "API get total products from cart")
    @GetMapping(UrlConstant.Cart.GET_TOTAL_PRODUCTS)
    public ResponseEntity<?> getTotalProducts(@PathVariable Integer customerId) {
        return VsResponseUtil.success(cartService.getTotalProducts(customerId));
    }

    @Operation(summary = "API add product to cart")
    @PostMapping(UrlConstant.Cart.ADD_PRODUCT_TO_CART)
    public ResponseEntity<?> addProductToCart(@Valid @RequestBody AddProductToCartResponseDto responseDto,
                                              @CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(cartService.addProductToCart(responseDto, userDetails.getCustomerId()));
    }

    @Operation(summary = "API get products from cart")
    @GetMapping(UrlConstant.Cart.GET_PRODUCTS_FROM_CART)
    public ResponseEntity<?> getProductsFromCart(@CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(cartService.getProductsFromCart(userDetails.getCustomerId()));
    }
}
