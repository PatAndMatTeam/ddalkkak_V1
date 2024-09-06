package com.ddalkkak.splitting.board.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record BoardCreateRequest(
         String category,
         String title,
         String content,
         String writer
) { }

