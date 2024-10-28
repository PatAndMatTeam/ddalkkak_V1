package com.ddalkkak.splitting.user.dto;

import com.ddalkkak.splitting.user.instrastructure.entity.UserEntity;
import jakarta.security.auth.message.AuthException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Builder
public record OAuth2UserInfo(
        String provider,
        String name,
        String email,
        String profile
) {

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            default -> throw new RuntimeException("ILLEGAL_REGISTRATION_ID");
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .provider("GOOGLE")
                .profile((String) attributes.get("picture"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .provider("KAKAO")
                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2UserInfo.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .provider("NAVER")
                .profile((String) response.get("profile_image"))
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .nickname(name)
                .userId(email)
                .provider(provider)
                .role(RoleUser.USER)
                .build();
    }
}
