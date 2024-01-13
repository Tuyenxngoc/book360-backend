package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;

public interface CustomerService {

    PaginationResponseDto<GetProductsResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request);

    boolean checkFavoriteProduct(int customerId, int productId);

    CommonResponseDto addFavoriteProduct(int customerId, int productId);

    CommonResponseDto removeFavoriteProduct(int customerId, int productId);

}
