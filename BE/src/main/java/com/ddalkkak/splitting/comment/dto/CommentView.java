package com.ddalkkak.splitting.comment.dto;

import com.ddalkkak.splitting.comment.instrastructure.entitiy.CommentEntity;
import lombok.Builder;

@Builder
public record CommentView(
        String writer,
        String content,
        String createdAt
){

    public static CommentView from(CommentEntity entity){
        return CommentView.builder()
                .writer(entity.getUserId())
                .content(entity.getContent())
                .createdAt(entity.getCreateDate().toString())
                .build();
    }

}