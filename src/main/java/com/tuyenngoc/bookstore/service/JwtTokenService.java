package com.tuyenngoc.bookstore.service;

public interface JwtTokenService {

    void saveTokenToRedis(String token);

    void deleteTokenFromRedis(String token);

    boolean checkTokenExistenceInRedis(String token);

}
