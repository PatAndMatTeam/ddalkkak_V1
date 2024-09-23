package com.ddalkkak.splitting.comment.dto;

import com.ddalkkak.splitting.comment.instrastructure.entitiy.CommentEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDto {

    private String password;


    public static CommentDto from(CommentEntity entity){
        return CommentDto.builder()
                .password(entity.getUserPw())
                .build();
    }
}
