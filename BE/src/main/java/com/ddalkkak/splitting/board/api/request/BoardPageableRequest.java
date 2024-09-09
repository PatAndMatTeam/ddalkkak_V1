package com.ddalkkak.splitting.board.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BoardPageableRequest(
        @Schema(description = "시작번호", example = "1")
        @Min(0)
        @NotBlank(message = "값을 입력해주세요")
        int start,
        @Schema(description = "종료번호", example = "10")
        @Min(0)
        @NotBlank(message = "값을 입력해주세요")
        int end
) {
}
