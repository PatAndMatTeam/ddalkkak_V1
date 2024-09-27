package com.ddalkkak.splitting.comment.dto;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.instrastructure.entitiy.CommentEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentCreateDto {


    String content;

    Long parentId;
    String userId;
    String userPw;
    Long boardId;

    public CommentEntity toEntity(){
        return CommentEntity.builder()
                .board(BoardEntity.builder()
                        .id(this.boardId)
                        .build())
                .content(this.content)
                .userId(this.userId)
                .userPw(this.userPw)
                .parentId(this.parentId)
                .build();
    }

    public static CommentCreateDto from(Long boardId, CommentCreateRequest commentCreateRequest){
        return CommentCreateDto.builder()
                .boardId(boardId)
                .content(commentCreateRequest.content())
                .userId(commentCreateRequest.writer())
                .userPw(commentCreateRequest.password())
                .parentId(commentCreateRequest.parentId())
                .build();
    }
}
