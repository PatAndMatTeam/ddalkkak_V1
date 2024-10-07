package com.ddalkkak.splitting.user.api.request;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UserPasswordVerifyRequest(
        String userId,
        String password
) {
}
