package com.ddalkkak.splitting.user.domain;

import com.ddalkkak.splitting.user.dto.OAuth2UserInfo;
import com.ddalkkak.splitting.user.dto.RoleUser;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class Account {

    private String userId;

    private String name;

    private RoleUser role;

    private String provider;

    private String refreshToken;


    public static Account from(OAuth2UserInfo user){
        return Account.builder()
                .userId(user.email())
                .name(user.name())
                .role(RoleUser.USER)
                .provider(user.provider())
                .build();
    }


    public Account updateRefreshToken(String token){
        return Account.builder()
                .userId(this.userId)
                .name(this.name)
                .role(this.role)
                .provider(this.provider)
                .refreshToken(token)
                .build();
    }
}
