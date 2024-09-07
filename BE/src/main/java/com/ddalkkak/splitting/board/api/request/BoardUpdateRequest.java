package com.ddalkkak.splitting.board.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BoardUpdateRequest(
        @NotBlank(message = "제목을 입력해주세요")
         String title,
        @NotBlank(message = "내용을 입력해주세요")
         String content
){ }
