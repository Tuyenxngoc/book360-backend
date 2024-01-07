package com.bookstore.bookstore.security.jwt;

import com.bookstore.bookstore.security.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final String CLAIM_TYPE = "type";
    private final String TYPE_ACCESS = "access";
    private final String TYPE_REFRESH = "refresh";
    private final String USERNAME_KEY = "username";
    private final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access.expiration_time}")
    private Integer EXPIRATION_TIME_ACCESS_TOKEN;

    @Value("${jwt.refresh.expiration_time}")
    private Integer EXPIRATION_TIME_REFRESH_TOKEN;

    public String generateToken(CustomUserDetails userDetails, Boolean isRefreshToken) {
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Map<String, Object> claim = new HashMap<>();
        claim.put(CLAIM_TYPE, isRefreshToken ? TYPE_REFRESH : TYPE_ACCESS);
        claim.put(USERNAME_KEY, userDetails.getUsername());
        claim.put(AUTHORITIES_KEY, authorities);
        if (isRefreshToken) {
            return Jwts.builder()
                    .setClaims(claim)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + (EXPIRATION_TIME_REFRESH_TOKEN * 60 * 1000L)))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(userDetails.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (EXPIRATION_TIME_ACCESS_TOKEN * 60 * 1000L)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    public String extractSubjectFromJwt(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

}
