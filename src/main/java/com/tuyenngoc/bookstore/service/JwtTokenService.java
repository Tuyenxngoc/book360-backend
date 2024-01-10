package com.tuyenngoc.bookstore.service;

public interface JwtTokenService {

    void saveAccessTokenToRedis(String accessToken, String username);

    void saveRefreshTokenToRedis(String refreshToken, String username);

    boolean checkAccessTokenExistenceInRedis(String accessToken, String username);

    boolean checkRefreshTokenExistenceInRedis(String refreshToken, String username);

    void deleteTokenFromRedis(String username);

}
