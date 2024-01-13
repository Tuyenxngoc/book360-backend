package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationRequestDto;
import com.tuyenngoc.bookstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestApiV1
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "API get products")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS)
    public ResponseEntity<?> getProducts(@Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProducts(requestDto));
    }

    @Operation(summary = "API get products by categoryId")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_BY_CATEGORY_ID)
    public ResponseEntity<?> getProductByCategoryId(@PathVariable int categoryId, @Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsByCategoryId(categoryId, requestDto));
    }

    @Operation(summary = "API get product detail")
    @GetMapping(UrlConstant.Product.GET_PRODUCT_DETAIL)
    public ResponseEntity<?> getProductDetail(@PathVariable int productId) {
        return VsResponseUtil.success(productService.getProductDetail(productId));
    }

    @Operation(summary = "API get products same author")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_SAME_AUTHOR)
    public ResponseEntity<?> getProductSameAuthor(@PathVariable int productId, @Valid @ParameterObject PaginationRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsSameAuthor(productId, requestDto));
    }
}
