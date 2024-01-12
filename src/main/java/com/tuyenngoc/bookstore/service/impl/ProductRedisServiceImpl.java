package com.tuyenngoc.bookstore.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuyenngoc.bookstore.domain.dto.pagination.PaginationResponseDto;
import com.tuyenngoc.bookstore.domain.dto.response.GetProductsResponseDto;
import com.tuyenngoc.bookstore.service.ProductRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRedisServiceImpl implements ProductRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private static final TypeReference<PaginationResponseDto<GetProductsResponseDto>> RESPONSE_DTO_TYPE = new TypeReference<>() {
    };

    @Override
    public String getKeyFrom(int categoryId, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        Sort sort = pageable.getSort();
        return String.format("all_products: %d: %d: %d: %s", categoryId, pageNumber, pageSize, sort).toUpperCase();
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getProducts(int categoryId, Pageable pageable) {
        try {
            String key = getKeyFrom(categoryId, pageable);
            String json = (String) redisTemplate.opsForValue().get(key);

            if (StringUtils.hasText(json)) {
                return objectMapper.readValue(json, RESPONSE_DTO_TYPE);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
        }
    }

}
