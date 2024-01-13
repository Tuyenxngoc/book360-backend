package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SuccessMessage;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.CommonResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.domain.entity.Customer;
import com.tuyenngoc.bookstore.domain.entity.Product;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.CustomerRepository;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import com.tuyenngoc.bookstore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request) {
//        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.PRODUCT);
//
//        Page<GetProductsResponseDto> page = customerRepository.getFavoriteProducts(customerId, pageable);
//        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.PRODUCT, page);
//
//        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
//        responseDto.setItems(page.getContent());
//        responseDto.setMeta(pagingMeta);

        return null;
    }

    @Override
    public boolean checkFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, String.valueOf(customerId)));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));
        return customer.getFavoriteProducts().contains(product);
    }

    @Override
    public CommonResponseDto addFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, String.valueOf(customerId)));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));
        customer.getFavoriteProducts().add(product);
        customerRepository.save(customer);
        return new CommonResponseDto(SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto removeFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, String.valueOf(customerId)));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));
        customer.getFavoriteProducts().remove(product);
        customerRepository.save(customer);
        return new CommonResponseDto(SuccessMessage.DELETE);
    }
}
