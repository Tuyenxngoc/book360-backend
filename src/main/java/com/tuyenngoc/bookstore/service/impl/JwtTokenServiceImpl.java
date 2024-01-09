package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.access.expiration_time}")
    private Integer EXPIRATION_TIME_ACCESS_TOKEN;

    @Override
    public void addToCache(String token) {
        redisTemplate.opsForValue().set(token, LocalDateTime.now().toString(), EXPIRATION_TIME_ACCESS_TOKEN, TimeUnit.MINUTES);
    }

    @Override
    public void setExpired(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public boolean isTokenPresentInCache(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
