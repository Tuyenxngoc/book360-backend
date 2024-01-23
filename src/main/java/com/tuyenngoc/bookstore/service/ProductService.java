package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.request.CreateProductRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Product;

import java.util.List;

public interface ProductService {

    PaginationResponseDto<GetProductsResponseDto> findProducts(PaginationFullRequestDto requestDto);

    PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto requestDto);

    PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryId, PaginationFullRequestDto request);

    PaginationResponseDto<GetProductsResponseDto> getProductsByAuthorId(int authorId, PaginationFullRequestDto requestDto);

    List<GetProductsResponseDto> getProductsSameAuthor(int productId, PaginationRequestDto request);

    GetProductDetailResponseDto getProductDetail(int productId);

    int getStockQuantityProducts();

    PaginationResponseDto<Product> getProductsForAdmin(PaginationFullRequestDto requestDto);

    Product createProduct(CreateProductRequestDto productDto);

    Product getProduct(int productId);

    CommonResponseDto deleteProduct(int productId);
}
