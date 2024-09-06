package com.ddalkkak.splitting.board.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record BoardUpdateRequest(
         String title,
         String content
){ }
