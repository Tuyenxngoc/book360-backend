package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRedisService {

    PaginationResponseDto<GetProductResponseDto> getProducts(Integer categoryId, Pageable pageable);

    void saveProducts(int categoryId, PaginationResponseDto<GetProductResponseDto> responseDto, Pageable pageable);

    GetProductDetailResponseDto getProductDetails(int productId);

    void saveProductDetails(int productId, GetProductDetailResponseDto responseDto);

    List<GetProductResponseDto> getProductsSameAuthor(int productId, Pageable pageable);

    void saveProductsSameAuthor(int productId, List<GetProductResponseDto> responseDto, Pageable pageable);

    void clearAllProductCache();

    void clearProductDetailCache(int productId);

    void clearProductSameAuthorCache(int productId);

}
