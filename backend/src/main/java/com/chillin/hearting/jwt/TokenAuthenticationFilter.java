package com.chillin.hearting.jwt;

import com.chillin.hearting.api.response.ResponseDTO;
import com.chillin.hearting.exception.JwtExpiredException;
import com.chillin.hearting.oauth.domain.PrincipalDetails;
import com.chillin.hearting.util.HeaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String headerToken = HeaderUtil.getAccessToken(request);
        log.debug("헤더로 넘어온 토큰 : {}", headerToken);
        AuthToken token = tokenProvider.convertAuthToken(headerToken);

        try {
            if (token.validate()) {
                log.debug("헤더로 넘어온 토큰이 null이 아니네!!!!");

                Authentication authentication = tokenProvider.getAuthentication(token);

                PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
                request.setAttribute("user", principalDetails.getUser());

                log.debug("TokenAuthenticationFilter로 접근한 user id(PK) : {}, 닉네임 : {}", principalDetails.getUser().getId(), principalDetails.getUser().getNickname());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtExpiredException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .status("fail")
                    .message("reissue")
                    .build();
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            // writeValueAsString() : Object to JSON in String
            response.getWriter().write(objectMapper.writeValueAsString(responseDTO));
            return;
        }
        filterChain.doFilter(request, response);

    }
}