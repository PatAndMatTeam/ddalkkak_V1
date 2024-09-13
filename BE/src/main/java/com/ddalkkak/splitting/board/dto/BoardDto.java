package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;


@Builder
public record BoardDto(
        Long id,
        String title,
        String content,
        String category,
        String createDate,
        String writer,
        List<UploadFileDto> files
){
    public static BoardDto from(BoardUpdateRequest updateRequest){
        return BoardDto.builder()
                .title(updateRequest.title())
                .content(updateRequest.content())
                .build();
    }

    public static BoardDto from(BoardEntity entity){
        return BoardDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory().name())
                .createDate(entity.getCreateDate().toString())
                .writer(entity.getWriter())
                .files(entity.getFiles().stream()
                        .map(UploadFileDto::from).collect(Collectors.toList())
                )
                .build();
    }

}
