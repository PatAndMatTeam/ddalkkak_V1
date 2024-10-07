package com.ddalkkak.splitting.board.domain;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Board {
    private final Long id;
    private final String title;
    private final String content;
    private final Long leftCnt;
    private final Long rightCnt;
    private final String category;
    private final String writer;
    private final List<String> comments;
    private final List<UploadFile> files;

    @Builder
    public Board(Long id, String title, String content, Long leftCnt, Long rightCnt,
                 String category, String writer, List<String> comments, List<UploadFile> files ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.leftCnt = leftCnt;
        this.rightCnt = rightCnt;
        this.category = category;
        this.writer = writer;
        this.comments = comments;
        this.files = files;

    }

    public static Board from(BoardCreateRequest boardCreate, List<UploadFile> uploadFile){
        return Board.builder()
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .category(boardCreate.getCategory())
                .writer(boardCreate.getWriter())
                .files(uploadFile)
                .build();
    }

    public Board update(BoardUpdateRequest boardUpdate, List<UploadFile> uploadFile){
        return Board.builder()
                .id(id)
                .title(boardUpdate.getTitle())
                .content(boardUpdate.getContent())
                .leftCnt(leftCnt)
                .rightCnt(rightCnt)
                .category(category)
                .writer(writer)
                .files(uploadFile)
                .build();
    }

}
