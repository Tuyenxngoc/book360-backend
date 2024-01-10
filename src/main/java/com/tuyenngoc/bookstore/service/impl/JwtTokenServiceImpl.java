package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_";
    private static final String REFRESH_TOKEN_KEY = "REFRESH_TOKEN_";

    @Value("${jwt.access.expiration_time}")
    private Integer EXPIRATION_TIME_ACCESS_TOKEN;

    @Value("${jwt.refresh.expiration_time}")
    private Integer EXPIRATION_TIME_REFRESH_TOKEN;

    private final RedisTemplate<String, Object> redisTemplate;

    private String getAccessTokenKey(String username) {
        return ACCESS_TOKEN_KEY + username;
    }

    private String getRefreshTokenKey(String username) {
        return REFRESH_TOKEN_KEY + username;
    }

    @Override
    public void saveAccessTokenToRedis(String accessToken, String username) {
        redisTemplate.opsForValue().set(getAccessTokenKey(username), accessToken, EXPIRATION_TIME_ACCESS_TOKEN, TimeUnit.MINUTES);
    }

    @Override
    public void saveRefreshTokenToRedis(String refreshToken, String username) {
        redisTemplate.opsForValue().set(getRefreshTokenKey(username), refreshToken, EXPIRATION_TIME_REFRESH_TOKEN, TimeUnit.MINUTES);
    }

    @Override
    public boolean checkAccessTokenExistenceInRedis(String accessToken, String username) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getAccessTokenKey(username))) &&
                accessToken.equals(redisTemplate.opsForValue().get(getAccessTokenKey(username)));
    }

    @Override
    public boolean checkRefreshTokenExistenceInRedis(String refreshToken, String username) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getRefreshTokenKey(username))) &&
                refreshToken.equals(redisTemplate.opsForValue().get(getRefreshTokenKey(username)));
    }

    @Override
    public void deleteTokenFromRedis(String username) {
        redisTemplate.delete(getAccessTokenKey(username));
        redisTemplate.delete(getRefreshTokenKey(username));
    }
}
