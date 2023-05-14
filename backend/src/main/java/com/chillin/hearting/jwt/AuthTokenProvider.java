package com.chillin.hearting.jwt;

import com.chillin.hearting.db.repository.UserRepository;
import com.chillin.hearting.exception.TokenValidFailedException;
import com.chillin.hearting.exception.UnAuthorizedException;
import com.chillin.hearting.oauth.domain.PrincipalDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AuthTokenProvider {
    private final Key key;
    private static final String AUTHORITIES_KEY = "role";
    private final UserRepository userRepository;


    /**
     * 객체 초기화
     *
     * @param secret : jwt의 secret
     */
    public AuthTokenProvider(String secret, UserRepository userRepository) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userRepository = userRepository;
    }

    public AuthToken createAuthToken(Date expiry) {
        return new AuthToken(expiry, key);
    }

    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        log.debug("getAuthentication 메서드로 들어왔당");
        if (authToken.validate()) {
            Claims claims = authToken.getTokenClaims();
            return getRealAuthentication(claims, authToken);
        } else {
            throw new TokenValidFailedException();
        }
    }

    public Authentication getExpiredUser(AuthToken authToken) {

        Claims claims = authToken.getExpiredTokenClaims();

        if (claims == null) {
            throw new UnAuthorizedException("다시 로그인 해주세요.");
        }
        return getRealAuthentication(claims, authToken);
    }

    public Authentication getRealAuthentication(Claims claims, AuthToken authToken) {

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        log.debug("claims subject := [{}]", claims.getSubject());

        PrincipalDetails principalDetails = new PrincipalDetails(userRepository.findById(String.valueOf(claims.get("id"))).orElse(null));
        return new UsernamePasswordAuthenticationToken(principalDetails, authToken, authorities);
    }

}