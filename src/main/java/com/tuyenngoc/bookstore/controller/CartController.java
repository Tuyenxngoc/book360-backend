package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.CurrentUser;
import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.CartDetailDto;
import com.tuyenngoc.bookstore.security.CustomUserDetails;
import com.tuyenngoc.bookstore.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "API get total products from cart")
    @GetMapping(UrlConstant.Cart.GET_TOTAL_PRODUCTS)
    public ResponseEntity<?> getTotalProducts(@CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(cartService.getTotalProducts(userDetails.getCustomerId()));
    }

    @Operation(summary = "API add product to cart")
    @PostMapping(UrlConstant.Cart.ADD_PRODUCT_TO_CART)
    public ResponseEntity<?> addProductToCart(
            @Valid @RequestBody CartDetailDto requestDto,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(cartService.addProductToCart(userDetails.getCustomerId(), requestDto));
    }

    @Operation(summary = "API get products from cart")
    @GetMapping(UrlConstant.Cart.GET_PRODUCTS_FROM_CART)
    public ResponseEntity<?> getProductsFromCart(@CurrentUser CustomUserDetails userDetails) {
        return VsResponseUtil.success(cartService.getProductsFromCart(userDetails.getCustomerId()));
    }

    @Operation(summary = "API update cart detail")
    @PutMapping(UrlConstant.Cart.UPDATE_CART_DETAIL)
    public ResponseEntity<?> updateCartDetail(
            @Valid @RequestBody CartDetailDto requestDto,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(cartService.updateCartDetail(userDetails.getCustomerId(), requestDto));
    }

    @Operation(summary = "API delete product from cart")
    @DeleteMapping(UrlConstant.Cart.DELETE_PRODUCT)
    public ResponseEntity<?> deleteProductFromCart(
            @PathVariable int productId,
            @CurrentUser CustomUserDetails userDetails
    ) {
        return VsResponseUtil.success(cartService.deleteProductFromCart(userDetails.getCustomerId(), productId));
    }
}
