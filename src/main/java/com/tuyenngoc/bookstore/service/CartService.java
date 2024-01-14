package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.CartDetailDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.ProductFromCartResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Cart;
import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import com.tuyenngoc.bookstore.domain.entity.Product;

import java.util.List;

public interface CartService {

    Cart createNewCart(int customerId);

    CartDetail createNewCartDetail(Cart cart, Product product);

    int getTotalProducts(int customerId);

    CommonResponseDto addProductToCart(int customerId, CartDetailDto responseDto);

    List<ProductFromCartResponseDto> getProductsFromCart(int customerId);

    CommonResponseDto updateCartDetail(int customerId, CartDetailDto cartDetailDto);

    CommonResponseDto deleteProductFromCart(int customerId, int productId);
}
