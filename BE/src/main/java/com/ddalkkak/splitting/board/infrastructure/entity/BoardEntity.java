package com.ddalkkak.splitting.board.infrastructure.entity;

import com.ddalkkak.splitting.comment.instrastructure.entitiy.CommentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="BOARD")
@Entity
public class BoardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long leftCnt;

    private Long rightCnt;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String writer;

    @Builder.Default
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFileEntity> files = new ArrayList<>();


    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeLeftCnt(long leftCnt){
        this.leftCnt = leftCnt;
    }

    public void changeRightCnt(long rightCnt){
        this.rightCnt = rightCnt;
    }

    // Helper methods to manage replies
    public void addReply(CommentEntity reply) {
        this.comments.add(reply);
    }

    public void removeReply(CommentEntity reply) {
        comments.remove(reply);
    }

    public void addFile(UploadFileEntity file) {
        getFiles().add(file);

        file.addBoard(this);
    }

    public void removeFile(UploadFileEntity file) {
        files.remove(file);
    }

}
