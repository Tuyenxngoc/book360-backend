package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRedisService {

    PaginationResponseDto<GetProductsResponseDto> getProducts(Integer categoryId, Pageable pageable);

    void saveProducts(int categoryId, PaginationResponseDto<GetProductsResponseDto> responseDto, Pageable pageable);

    GetProductDetailResponseDto getProductDetails(int productId);

    void saveProductDetails(int productId, GetProductDetailResponseDto responseDto);

    List<GetProductsResponseDto> getProductsSameAuthor(int productId, Pageable pageable);

    void saveProductsSameAuthor(int productId, List<GetProductsResponseDto> responseDto, Pageable pageable);

}
