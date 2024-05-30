package com.tuyenngoc.bookstore.service.impl;

import com.tuyenngoc.bookstore.service.UploadRedisService;
import com.tuyenngoc.bookstore.util.UploadFileUtil;
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

    private final UploadFileUtil uploadFileUtil;

    public String getKeyFrom(String username) {
        return String.format("upload: %s", username).toUpperCase();
    }

    @Override
    public void saveUrls(String username, List<String> urls) {
        String key = getKeyFrom(username);
        redisTemplate.opsForList().rightPushAll(key, urls);
    }

    @Override
    public List<String> getUrls(String username, String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public void deleteUrls(String username, List<String> urls) {
        String key = getKeyFrom(username);
        for (String url : getUrls(username, key)) {
            if (!urls.contains(url)) {
                uploadFileUtil.destroyFileWithUrl(url);
            }
        }
        redisTemplate.delete(key);
    }

    @Override
    public void deleteUrlFromRedisList(String username, String url) {
        String key = getKeyFrom(username);
        List<String> urls = getUrls(username, key);

        // Remove the URL from the Redis list
        redisTemplate.opsForList().remove(key, 1, url);
    }

}
