package com.ddalkkak.splitting.board.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BoardPageableRequest(
        @Min(0)
        @NotBlank(message = "값을 입력해주세요")
        int start,
        @Min(0)
        @NotBlank(message = "값을 입력해주세요")
        int end
) {
}
