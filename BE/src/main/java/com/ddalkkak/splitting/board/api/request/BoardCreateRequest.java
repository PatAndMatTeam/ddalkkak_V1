package com.ddalkkak.splitting.board.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BoardCreateRequest(
        @Schema(description = "카테고리", example = "정치")
        @NotBlank(message = "카테고리를 입력해주세요")
         String category,

        @Schema(description = "제목", example = "우리 성공할 수 있을까?")
        @NotBlank(message = "제목을 입력해주세요")
         String title,

        @Schema(description = "내용", example = "무조건 성공 해야지 어?")
        @NotBlank(message = "내용을 입력해주세요")
         String content,

        @Schema(description = "작성자", example = "윤주영")
        @NotBlank(message = "작성자를 입력해주세요")
         String writer
) { }

