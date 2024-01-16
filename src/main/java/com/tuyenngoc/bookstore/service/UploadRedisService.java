package com.tuyenngoc.bookstore.service;

public interface UploadRedisService {

    void saveUrl(String username, String url);

    String getUrl(String username);

}
