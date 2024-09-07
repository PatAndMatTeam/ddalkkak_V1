package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import lombok.Builder;


@Builder
public record BoardCreateDto(
         String category,
         String title,
         String content,
         String writer
) {

    public static BoardCreateDto from(BoardCreateRequest createRequest){
        return BoardCreateDto.builder()
                .category(createRequest.category())
                .title(createRequest.title())
                .content(createRequest.content())
                .writer(createRequest.writer())
                .build();
    }
}
