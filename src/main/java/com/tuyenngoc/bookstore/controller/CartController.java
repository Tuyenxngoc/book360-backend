package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.service.impl.CartDetailServiceImpl;
import com.tuyenngoc.bookstore.service.impl.CartServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestApiV1
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;

    private final CartDetailServiceImpl cartDetailService;

    @Operation(summary = "API get total products from cart")
    @GetMapping(UrlConstant.Cart.GET_TOTAL_PRODUCTS)
    public ResponseEntity<?> getTotalProducts(@PathVariable Integer customerId) {
        return VsResponseUtil.success(cartService.getTotalProducts(customerId));
    }
}
