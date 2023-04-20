package com.chillin.hearting.config;

import com.chillin.hearting.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

//    @Bean
//    public AuthTokenProvider jwtProvider() {
//        return new AuthTokenProvider(secret, userRepository);
//    }

}