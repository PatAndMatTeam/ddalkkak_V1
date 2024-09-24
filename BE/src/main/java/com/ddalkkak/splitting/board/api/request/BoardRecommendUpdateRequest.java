package com.ddalkkak.splitting.board.api.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record BoardRecommendUpdateRequest(
        @Min(0)
        long leftRecommend,
        @Min(0)
        long rightRecommend
) {
}
