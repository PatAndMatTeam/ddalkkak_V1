package com.ddalkkak.splitting.board.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;



@Builder
public record BoardUpdateRequest(
        @Schema(description = "제목", example = "나 이걸로 수정한다")
        @NotBlank(message = "제목을 입력해주세요")
         String title,

        @Schema(description = "내용", example = "무조건 성공")
        @NotBlank(message = "내용을 입력해주세요")
         String content
){ }
