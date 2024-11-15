package com.ddalkkak.splitting.board.api.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record BoardVoteUpdateRequest(
        @Min(0)
        long leftVote,
        @Min(0)
        long rightVote
) {
}
