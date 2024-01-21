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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "API find product")
    @GetMapping(UrlConstant.Product.FIND_PRODUCT)
    public ResponseEntity<?> findProduct(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.findProduct(requestDto));
    }

    @Operation(summary = "API get products by category id")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_BY_CATEGORY_ID)
    public ResponseEntity<?> getProductByCategoryId(@PathVariable int categoryId, @Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsByCategoryId(categoryId, requestDto));
    }

    @Operation(summary = "API get products by author id")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_BY_AUTHOR_ID)
    public ResponseEntity<?> getProductByAuthorId(@PathVariable int authorId, @Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductByAuthorId(authorId, requestDto));
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

    @PreAuthorize(value = "hasRole('ADMIN')")
    @Operation(summary = "API get quantity products")
    @GetMapping(UrlConstant.Product.GET_QUANTITY_PRODUCTS)
    public ResponseEntity<?> getRevenue() {
        return VsResponseUtil.success(productService.getQuantityProducts());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "API get all products")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_ADMIN)
    public ResponseEntity<?> getProductsForAdmin(@Valid @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsForAdmin(requestDto));
    }
}
