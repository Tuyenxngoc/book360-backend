package com.tuyenngoc.bookstore.controller;

import com.tuyenngoc.bookstore.annotation.RestApiV1;
import com.tuyenngoc.bookstore.base.VsResponseUtil;
import com.tuyenngoc.bookstore.constant.UrlConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.CreateProductRequestDto;
import com.tuyenngoc.bookstore.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "API get products")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS)
    public ResponseEntity<?> getProducts(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProducts(requestDto));
    }

    @Operation(summary = "API find product")
    @GetMapping(UrlConstant.Product.FIND_PRODUCT)
    public ResponseEntity<?> findProducts(@ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.findProducts(requestDto));
    }

    @Operation(summary = "API get products by category id")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_BY_CATEGORY_ID)
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable int categoryId, @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsByCategoryId(categoryId, requestDto));
    }

    @Operation(summary = "API get products by author id")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_BY_AUTHOR_ID)
    public ResponseEntity<?> getProductsByAuthorId(@PathVariable int authorId, @ParameterObject PaginationFullRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsByAuthorId(authorId, requestDto));
    }

    @Operation(summary = "API get products same author by product id")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_SAME_AUTHOR)
    public ResponseEntity<?> getProductsSameAuthor(@PathVariable int productId, @ParameterObject PaginationRequestDto requestDto) {
        return VsResponseUtil.success(productService.getProductsSameAuthor(productId, requestDto));
    }

    @Operation(summary = "API get product detail")
    @GetMapping(UrlConstant.Product.GET_PRODUCT_DETAIL)
    public ResponseEntity<?> getProductDetail(@PathVariable int productId) {
        return VsResponseUtil.success(productService.getProductDetail(productId));
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @Tag(name = "Product controller admin")
    @Operation(summary = "API get stock quantity products")
    @GetMapping(UrlConstant.Product.GET_STOCK_QUANTITY_PRODUCTS)
    public ResponseEntity<?> getStockQuantityProducts() {
        return VsResponseUtil.success(productService.getStockQuantityProducts());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Product controller admin")
    @Operation(summary = "API get products")
    @GetMapping(UrlConstant.Product.GET_PRODUCTS_FOR_ADMIN)
    public ResponseEntity<?> getProductsForAdmin(@ParameterObject PaginationFullRequestDto requestDto,
                                                 @RequestParam(name = "sellerStockMax", required = false, defaultValue = "0")
                                                 @Parameter(description = "Maximum seller stock quantity")
                                                         int sellerStockMax,
                                                 @RequestParam(name = "sellerStockMin", required = false, defaultValue = "0")
                                                 @Parameter(description = "Minimum seller stock quantity")
                                                         int sellerStockMin,
                                                 @RequestParam(name = "soldMax", required = false, defaultValue = "0")
                                                 @Parameter(description = "Maximum quantity sold")
                                                         int soldMax,
                                                 @RequestParam(name = "soldMin", required = false, defaultValue = "0")
                                                 @Parameter(description = "Minimum quantity sold")
                                                         int soldMin,
                                                 @RequestParam(name = "categoryId", required = false, defaultValue = "0")
                                                 @Parameter(description = "Filter by category ID")
                                                         int categoryId
    ) {
        return VsResponseUtil.success(productService.getProductsForAdmin(requestDto, sellerStockMax, sellerStockMin, soldMax, soldMin, categoryId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Product controller admin")
    @Operation(summary = "API get product")
    @GetMapping(UrlConstant.Product.GET_PRODUCT)
    public ResponseEntity<?> getProduct(@PathVariable int productId) {
        return VsResponseUtil.success(productService.getProduct(productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Product controller admin")
    @Operation(summary = "API create and update products")
    @PutMapping(UrlConstant.Product.CREATE_PRODUCTS)
    public ResponseEntity<?> createProducts(@Valid @RequestBody CreateProductRequestDto productDto) {
        return VsResponseUtil.success(productService.createProduct(productDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Tag(name = "Product controller admin")
    @Operation(summary = "API delete product")
    @DeleteMapping(UrlConstant.Product.DELETE_PRODUCT)
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
        return VsResponseUtil.success(productService.deleteProduct(productId));
    }
}
