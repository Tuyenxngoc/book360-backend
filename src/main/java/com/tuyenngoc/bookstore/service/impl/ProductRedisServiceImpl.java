package com.tuyenngoc.bookstore.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductDetailResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.service.ProductRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRedisServiceImpl implements ProductRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private static final TypeReference<PaginationResponseDto<GetProductsResponseDto>> PRODUCTS_LIST_RESPONSE_TYPE =
            new TypeReference<>() {
            };

    private static final TypeReference<GetProductDetailResponseDto> PRODUCT_DETAIL_RESPONSE_DTO_TYPE =
            new TypeReference<>() {
            };

    private static final TypeReference<List<GetProductsResponseDto>> PRODUCT_SAME_AUTHOR_RESPONSE_DTO_TYPE =
            new TypeReference<>() {
            };

    public String getKeyFrom(int categoryId, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Sort sort = pageable.getSort();
        return String.format("all_products: %d: %d: %d: %s", categoryId, pageNumber, pageSize, sort).toUpperCase();
    }

    public String getKeyFrom(int productId) {
        return String.format("product_details: %d", productId).toUpperCase();
    }

    public String getKeyFromProductSameAuthor(int productId, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        return String.format("product_same_author: %d: %d: %d", productId, pageNumber, pageSize).toUpperCase();
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProducts(int categoryId, Pageable pageable) {
        try {
            String key = getKeyFrom(categoryId, pageable);
            String json = (String) redisTemplate.opsForValue().get(key);

            if (StringUtils.hasText(json)) {
                return objectMapper.readValue(json, PRODUCTS_LIST_RESPONSE_TYPE);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Error fetching products from Redis: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void saveProducts(int categoryId, PaginationResponseDto<GetProductsResponseDto> responseDto, Pageable pageable) {
        try {
            String key = getKeyFrom(categoryId, pageable);
            String json = objectMapper.writeValueAsString(responseDto);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            log.error("Error saving products to Redis: {}", e.getMessage(), e);
        }
    }

    @Override
    public GetProductDetailResponseDto getProductDetails(int productId) {
        try {
            String key = getKeyFrom(productId);
            String json = (String) redisTemplate.opsForValue().get(key);

            if (StringUtils.hasText(json)) {
                return objectMapper.readValue(json, PRODUCT_DETAIL_RESPONSE_DTO_TYPE);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Error fetching product details from Redis: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void saveProductDetails(int productId, GetProductDetailResponseDto responseDto) {
        try {
            String key = getKeyFrom(productId);
            String json = objectMapper.writeValueAsString(responseDto);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            log.error("Error saving product details to Redis: {}", e.getMessage(), e);
        }
    }

    @Override
    public List<GetProductsResponseDto> getProductsSameAuthor(int productId, Pageable pageable) {
        try {
            String key = getKeyFromProductSameAuthor(productId, pageable);
            String json = (String) redisTemplate.opsForValue().get(key);

            if (StringUtils.hasText(json)) {
                return objectMapper.readValue(json, PRODUCT_SAME_AUTHOR_RESPONSE_DTO_TYPE);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Error fetching products from Redis: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void saveProductsSameAuthor(int productId, List<GetProductsResponseDto> responseDto, Pageable pageable) {
        try {
            String key = getKeyFromProductSameAuthor(productId, pageable);
            String json = objectMapper.writeValueAsString(responseDto);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            log.error("Error saving product details to Redis: {}", e.getMessage(), e);
        }
    }

}
