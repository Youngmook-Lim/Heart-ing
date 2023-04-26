package com.chillin.hearting.jwt;

import com.chillin.hearting.exception.JwtExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;
    private static final String AUTHORITIES_KEY = "role";


    public AuthToken(Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(expiry);
    }


    public AuthToken(String id, String role, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, role, expiry);
    }

    /**
     * key : Claim에 셋팅될 key 값
     * data : Claim에 셋팅 될 data 값
     * subject : payload에 sub의 value로 들어갈 subject값
     * expire : 토큰 유효기간 설정을 위한 값
     * jwt 토큰의 구성 : header+payload+signature
     */
    private String createAuthToken(Date expiry) {
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("createdDate", System.currentTimeMillis())
                .setSubject("refreshToken")
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String id, String role, Date expiry) {
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject("accessToken")
                .claim(AUTHORITIES_KEY, role)
                .claim("id", id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        log.debug("validate() 호출됨.");
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature - 시그니처 검증에 실패한 토큰입니다. jwt secret 키가 정상이 아닐 가능성이 높습니다.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token - 손상된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token - 유효기간이 만료된 토큰입니다.");
            throw new JwtExpiredException();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token - 지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.debug(e.getMessage());
            log.error("아마도 JWT가 헤더에 안 담겼을지도?");
        }
        return null;
    }

    public Claims getExpiredTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
//            return e.getClaims();
        }
        return null;

    }
}