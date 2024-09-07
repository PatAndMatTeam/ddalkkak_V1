package com.ddalkkak.splitting.board.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BoardCreateRequest(
        @NotBlank(message = "카테고리를 입력해주세요")
         String category,
        @NotBlank(message = "제목을 입력해주세요")
         String title,
        @NotBlank(message = "내용을 입력해주세요")
         String content,
        @NotBlank(message = "작성자를 입력해주세요")
         String writer
) { }

