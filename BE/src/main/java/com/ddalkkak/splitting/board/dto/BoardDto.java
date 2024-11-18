package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardVoteUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.comment.dto.CommentView;
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
        Long recommend,
        Long visited,
        Long leftVote,
        Long rightVote,
        List<UploadFileDto> files,
        List<CommentView> comments
){


    public static BoardDto from(BoardUpdateRequest updateRequest){
        return BoardDto.builder()
                .title(updateRequest.title())
                .content(updateRequest.content())
                .build();
    }

    public static BoardDto from(BoardVoteUpdateRequest boardVoteUpdateRequest){
        return BoardDto.builder()
                .leftVote(boardVoteUpdateRequest.leftVote())
                .rightVote(boardVoteUpdateRequest.rightVote())
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
                .leftVote(entity.getLeftVote())
                .rightVote(entity.getRightVote())
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
