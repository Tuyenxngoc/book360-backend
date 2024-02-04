package com.tuyenngoc.bookstore.service;

import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.request.CreateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.request.UpdateCustomerRequestDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetTodoResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {

    PaginationResponseDto<GetProductResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request);

    boolean checkFavoriteProduct(int customerId, int productId);

    CommonResponseDto addFavoriteProduct(int customerId, int productId);

    CommonResponseDto removeFavoriteProduct(int customerId, int productId);

    CommonResponseDto uploadAvatar(int customerId, MultipartFile file);

    List<String> uploadImages(String username, List<MultipartFile> files);

    long parseSize(String size);

    CommonResponseDto updateCustomer(int customerId, UpdateCustomerRequestDto updateCustomerRequestDto);

    GetTodoResponseDto getTodo(int customerId);

    long getCountCustomer();

    PaginationResponseDto<Customer> getCustomers(PaginationFullRequestDto requestDto);

    Customer getCustomer(int customerId);

    Customer createCustomer(CreateCustomerRequestDto requestDto);

}
