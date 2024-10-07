package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import com.ddalkkak.splitting.comment.dto.CommentDto;
import com.ddalkkak.splitting.comment.dto.CommentView;
import lombok.Builder;
import lombok.ToString;

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
        Long recommend,
        Long visited,
        Long leftCnt,
        Long rightCnt,
        List<UploadFileDto> files,
        List<CommentView> comments
){


    public static BoardDto from(BoardUpdateRequest updateRequest){
        return BoardDto.builder()
                .title(updateRequest.title())
                .content(updateRequest.content())
                .build();
    }

    public static BoardDto from(BoardRecommendUpdateRequest boardRecommendUpdateRequest){
        return BoardDto.builder()
                .leftCnt(boardRecommendUpdateRequest.leftRecommend())
                .rightCnt(boardRecommendUpdateRequest.rightRecommend())
                .build();
    }

    public static BoardDto from(BoardEntity entity){
        return BoardDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory().name())
                .createDate(entity.getCreateDate().toString())
                .recommend(entity.getRecommend())
                .leftCnt(entity.getLeftCnt())
                .rightCnt(entity.getRightCnt())
                .visited(entity.getVisited())
                .writer(entity.getWriter())
                .files(entity.getFiles().stream()
                        .map(UploadFileDto::from).collect(Collectors.toList())
                )
                .comments(entity.getComments().stream()
                        .map(CommentView::from).collect(Collectors.toList())
                )
                .build();
    }



}
