package com.chillin.hearting.oauth.info;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE) // 이 클래스 내부에서만 객체 생성 허용
public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String email;

    public static OAuth2Attribute of(String provider, Map<String, Object> attributes) {
        switch (provider) {
            case "kakao":
                return ofKakao(attributes);
            case "google":
                return ofGoogle(attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofKakao(Map<String, Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2Attribute.builder()
                .email((String) kakaoAccount.get("email"))
                .attributes(kakaoAccount)
                .build();

    }

    private static OAuth2Attribute ofGoogle(Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .attributes(attributes)
                .email((String) attributes.get("email"))
                .build();
    }
}
