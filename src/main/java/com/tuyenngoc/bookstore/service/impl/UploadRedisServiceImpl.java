package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.service.UploadRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadRedisServiceImpl implements UploadRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String getKeyFrom(String username) {
        return String.format("upload: %s", username).toUpperCase();
    }

    @Override
    public void saveUrls(String username, List<String> urls) {
        String key = getKeyFrom(username);
        redisTemplate.opsForList().rightPushAll(key, urls);
    }

    @Override
    public List<String> getUrls(String username) {
        String key = getKeyFrom(username);
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
