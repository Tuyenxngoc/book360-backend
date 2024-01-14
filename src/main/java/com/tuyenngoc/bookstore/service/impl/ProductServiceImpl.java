package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.constant.ErrorMessage;
import com.tuyenngoc.bookstore.constant.SortByDataConstant;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationFullRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationRequestDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.pagination.PagingMeta;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.exception.NotFoundException;
import com.tuyenngoc.bookstore.repository.ProductRepository;
import com.tuyenngoc.bookstore.service.ProductRedisService;
import com.tuyenngoc.bookstore.service.ProductService;
import com.tuyenngoc.bookstore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductRedisService productRedisService;

    @Override
    public PaginationResponseDto<GetProductsResponseDto> findProduct(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        Page<GetProductsResponseDto> page = productRepository.getProducts(requestDto.getKeyword(), pageable);
        PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        responseDto.setMeta(pagingMeta);

        return responseDto;
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProducts(PaginationFullRequestDto requestDto) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        return Optional.ofNullable(productRedisService.getProducts(-1, pageable))
                .orElseGet(() -> {
                    Page<GetProductsResponseDto> page = productRepository.getProducts(pageable);
                    PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

                    PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
                    responseDto.setItems(page.getContent());
                    responseDto.setMeta(pagingMeta);

                    productRedisService.saveProducts(-1, responseDto, pageable);

                    return responseDto;
                });
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProductsByCategoryId(int categoryId, PaginationFullRequestDto requestDto) {

        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.PRODUCT);

        return Optional.ofNullable(productRedisService.getProducts(categoryId, pageable))
                .orElseGet(() -> {
                    Page<GetProductsResponseDto> page = productRepository.getProductsByCategoryId(categoryId, pageable);
                    PagingMeta pagingMeta = PaginationUtil.buildPagingMeta(requestDto, SortByDataConstant.PRODUCT, page);

                    PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
                    responseDto.setItems(page.getContent());
                    responseDto.setMeta(pagingMeta);

                    productRedisService.saveProducts(categoryId, responseDto, pageable);

                    return responseDto;
                });
    }

    @Override
    public GetProductDetailResponseDto getProductDetail(int productId) {
        return Optional.ofNullable(productRedisService.getProductDetails(productId))
                .orElseGet(() -> {
                    GetProductDetailResponseDto productDetails = productRepository.getProductDetail(productId)
                            .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, String.valueOf(productId)));

                    productRedisService.saveProductDetails(productId, productDetails);

                    return productDetails;
                });
    }

    @Override
    public List<GetProductsResponseDto> getProductsSameAuthor(int productId, PaginationRequestDto request) {

        Pageable pageable = PaginationUtil.buildPageable(request);

        return Optional.ofNullable(productRedisService.getProductsSameAuthor(productId, pageable))
                .orElseGet(() -> {
                    Page<GetProductsResponseDto> page = productRepository.getProductsSameAuthor(productId, pageable);

                    productRedisService.saveProductsSameAuthor(productId, page.getContent(), pageable);

                    return page.getContent();
                });
    }
}
