package com.ddalkkak.splitting.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum RoleUser {

    SUPERADMIN("ROLE_SUPERADMIN,ROLE_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;
}
