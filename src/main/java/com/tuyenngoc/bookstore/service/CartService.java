package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.request.AddProductToCartResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ProductFromCartResponseDto;

import java.util.List;

public interface CartService {

    int getTotalProducts(Integer customerId);

    CommonResponseDto addProductToCart(AddProductToCartResponseDto responseDto, int customerId);

    List<ProductFromCartResponseDto> getProductsFromCart(int customerId);
}
