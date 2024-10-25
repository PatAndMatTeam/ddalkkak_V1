package com.ddalkkak.splitting.user.domain;

import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {

    private String userId;

    private String name;

    private String role;

    private String provider;



    public static User from(OAuth2UserInfo user){
        return User.builder()
                .userId(user.email())
                .name(user.name())
                .role("ROLE_USER")
                .provider(user.provider())
                .build();
    }
}
