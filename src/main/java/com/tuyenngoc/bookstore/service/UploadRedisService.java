package com.tuyenngoc.bookstore.service;

import java.util.List;

public interface UploadRedisService {

    void saveUrls(String username, List<String> urls);

    List<String> getUrls(String username, String key);

    void deleteUrls(String username, List<String> urls);

}
