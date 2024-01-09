package com.tuyenngoc.bookstore.service;

public interface JwtTokenService {

    void addToCache(String token);

    void setExpired(String token);

    boolean isTokenPresentInCache(String token);

}
