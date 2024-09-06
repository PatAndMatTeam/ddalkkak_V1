package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import lombok.Builder;



@Builder
public record BoardDto(
        String title,
        String content,
        String category,
        String writer
){
    public static BoardDto from(BoardEntity entity){
        return BoardDto.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory().name())
                .writer(entity.getWriter())
                .build();
    }
}
