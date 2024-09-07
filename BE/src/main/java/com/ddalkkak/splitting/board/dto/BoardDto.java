package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import lombok.Builder;



@Builder
public record BoardDto(
        Long id,
        String title,
        String content,
        String category,
        String createDate,
        String writer
){
    public static BoardDto from(BoardEntity entity){
        return BoardDto.builder()
                .id(entity.getIdx())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory().name())
                .writer(entity.getWriter())
                .createDate(entity.getCreateDate().toString())
                .build();
    }

    public static BoardDto from(BoardUpdateRequest updateRequest){
        return BoardDto.builder()
                .title(updateRequest.title())
                .content(updateRequest.content())
                .build();
    }
}
