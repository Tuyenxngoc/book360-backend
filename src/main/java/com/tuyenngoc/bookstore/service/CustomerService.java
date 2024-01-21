package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.request.UpdateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetTodoResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {

    PaginationResponseDto<GetProductsResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request);

    boolean checkFavoriteProduct(int customerId, int productId);

    CommonResponseDto addFavoriteProduct(int customerId, int productId);

    CommonResponseDto removeFavoriteProduct(int customerId, int productId);

    String uploadImage(String username, MultipartFile file);

    CommonResponseDto updateCustomer(int customerId, UpdateCustomerRequestDto updateCustomerRequestDto);

    GetTodoResponseDto getTodo(int customerId);

    int getCountCustomer();

    PaginationResponseDto<Customer> getCustomers(PaginationFullRequestDto requestDto);
}
