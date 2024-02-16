package com.tuyenngoc.bookstore.domain.dto.response.auth;

import com.tuyenngoc.bookstore.constant.TokenType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TokenRefreshResponseDto {

    private String tokenType = TokenType.BEARER.name();

    private String accessToken;

    private String refreshToken;

    public TokenRefreshResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
