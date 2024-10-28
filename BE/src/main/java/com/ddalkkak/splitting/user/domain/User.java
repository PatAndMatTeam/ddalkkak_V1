package com.ddalkkak.splitting.user.domain;

import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
import com.ddalkkak.splitting.user.dto.RoleUser;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class User {

    private String userId;

    private String name;

    private RoleUser role;

    private String provider;



    public static User from(OAuth2UserInfo user){
        return User.builder()
                .userId(user.email())
                .name(user.name())
                .role(RoleUser.USER)
                .provider(user.provider())
                .build();
    }
}
