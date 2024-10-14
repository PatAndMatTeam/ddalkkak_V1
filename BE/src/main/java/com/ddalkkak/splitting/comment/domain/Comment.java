package com.ddalkkak.splitting.comment.domain;

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
}
