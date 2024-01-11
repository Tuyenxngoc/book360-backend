package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;

public interface ProductService {

    PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto requestDto);

}
