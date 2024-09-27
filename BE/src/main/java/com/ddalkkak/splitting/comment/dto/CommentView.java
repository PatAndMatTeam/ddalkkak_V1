package com.ddalkkak.splitting.comment.dto;

import com.ddalkkak.splitting.comment.instrastructure.entitiy.CommentEntity;
import lombok.Builder;

@Builder
public record CommentView(
        Long id,
        String writer,
        String content,
        String createdAt
){

    public static CommentView from(CommentEntity entity){
        return CommentView.builder()
                .id(entity.getId())
                .writer(entity.getUserId())
                .content(entity.getContent())
                .createdAt(entity.getCreateDate().toString())
                .build();
    }

}