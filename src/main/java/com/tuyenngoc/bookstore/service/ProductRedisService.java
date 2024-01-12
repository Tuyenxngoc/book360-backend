package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import org.springframework.data.domain.Pageable;

public interface ProductRedisService {

    String getKeyFrom(int categoryId, Pageable pageable);

    PaginationResponseDto<GetProductsResponseDto> getProducts(int categoryId, Pageable pageable);

    void saveProducts(int categoryId, PaginationResponseDto<GetProductsResponseDto> responseDto, Pageable pageable);
}
