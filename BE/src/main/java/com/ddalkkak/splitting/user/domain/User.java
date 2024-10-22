package com.ddalkkak.splitting.user.domain;

import com.ddalkkak.splitting.user.dto.RoleUser;
import com.nimbusds.oauth2.sdk.Role;
import lombok.Getter;

@Getter
public class User {

    private String name;

    private Role role;
}
