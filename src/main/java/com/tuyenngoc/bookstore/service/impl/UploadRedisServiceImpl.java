package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.service.UploadRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadRedisServiceImpl implements UploadRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public String getKeyFrom(String username) {
        return String.format("upload: %s", username).toUpperCase();
    }

    @Override
    public void saveUrl(String username, String url) {
        String key = getKeyFrom(username);
        redisTemplate.opsForValue().set(key, url);
    }

    @Override
    public String getUrl(String username) {
        String key = getKeyFrom(username);
        return (String) redisTemplate.opsForValue().get(key);
    }
}
