package com.tuyenngoc.bookstore.domain.dto.response;

import com.tuyenngoc.bookstore.constant.TokenType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private String tokenType = TokenType.BEARER.name();

    private String accessToken;

    private String refreshToken;

    private Collection<? extends GrantedAuthority> authorities;

    public LoginResponseDto(String accessToken, String refreshToken, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.authorities = authorities;
    }
}
