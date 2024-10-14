package com.ddalkkak.splitting.board.domain;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
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

    private final Long visited;

    private final Long recommend;
    private final List<Comment> comments;
    private final List<UploadFile> files;
    private final List<Board> children;

    private final LocalDateTime modifiedDate;

    @Builder
    public Board(Long id, String title, String content, Long leftCnt, Long rightCnt,
                 String category, String writer, Long visited, Long recommend, List<Comment> comments,
                 List<Board> children, List<UploadFile> files, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.leftCnt = leftCnt;
        this.rightCnt = rightCnt;
        this.category = category;
        this.writer = writer;
        this.visited = visited;
        this.recommend = recommend;
        this.comments = comments;
        this.children = children;
        this.files = files;
        this.modifiedDate = modifiedDate;
    }

    public static Board from(BoardCreateRequest boardCreate){
        return Board.builder()
                .title(boardCreate.getTitle())
                .content(boardCreate.getContent())
                .category(boardCreate.getCategory())
                .writer(boardCreate.getWriter())
                .build();
    }

    public Board update(BoardUpdateRequest boardUpdate, List<UploadFile> uploadFile){
        return Board.builder()
                .id(id)
                .title(boardUpdate.title())
                .content(boardUpdate.content())
                .leftCnt(leftCnt)
                .rightCnt(rightCnt)
                .category(category)
                .writer(writer)
                .files(uploadFile)
                .build();
    }

}
