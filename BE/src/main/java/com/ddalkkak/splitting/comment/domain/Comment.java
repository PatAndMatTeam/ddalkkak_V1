package com.ddalkkak.splitting.comment.domain;

import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Comment {

    private Long id;
    private String writer;
    private String content;
    private LocalDateTime modifiedDate;
    private boolean isLeftLocation;
    private boolean isRightLocation;
    private Long parentId;
    private String userPw;

    public static Comment from(Long parentId, CommentCreateRequest request){
        return Comment.builder()
                .writer(request.writer())
                .content(request.content())
                .modifiedDate(LocalDateTime.now())
                .isLeftLocation(request.isLeftPosition())
                .isRightLocation(request.isRightPosition())
                .userPw(request.password())
                .parentId(parentId)
                .build();
    }
}
