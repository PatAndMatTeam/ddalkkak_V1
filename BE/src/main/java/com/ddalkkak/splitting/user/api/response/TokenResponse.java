package com.ddalkkak.splitting.user.api.response;

import lombok.Builder;

@Builder
public record TokenResponse(
        String accessToken
) {
}
