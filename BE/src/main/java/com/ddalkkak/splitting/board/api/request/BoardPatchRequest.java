package com.ddalkkak.splitting.board.api.request;

import lombok.Builder;

@Builder
public record BoardPatchRequest(
        String title,
        String content
) {
}
