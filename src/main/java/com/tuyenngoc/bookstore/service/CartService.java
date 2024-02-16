package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.CartDetailDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.product.GetProductFromCartResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Cart;
import com.tuyenngoc.bookstore.domain.entity.CartDetail;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.Product;

import java.util.List;

public interface CartService {

    Cart createNewCart(int customerId);

    Cart createNewCart(Customer customer);

    CartDetail createNewCartDetail(Cart cart, Product product);

    Cart getCartByCustomerId(int customerId);

    int getTotalProducts(int customerId);

    CommonResponseDto addProductToCart(int customerId, CartDetailDto requestDto);

    List<GetProductFromCartResponseDto> getProductsFromCart(int customerId);

    CommonResponseDto updateCartDetail(int customerId, CartDetailDto requestDto);

    CommonResponseDto deleteProductFromCart(int customerId, int productId);
}
