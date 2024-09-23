package com.ddalkkak.splitting.board.infrastructure.entity;

import com.ddalkkak.splitting.board.domain.UploadFile;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.reply.instrastructure.entitiy.ReplyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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

    @Enumerated(EnumType.STRING)
    private Category category;

    private String writer;

    @Builder.Default
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyEntity> replies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFileEntity> files = new ArrayList<>();


    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    // Helper methods to manage replies
    public void addReply(ReplyEntity reply) {
        this.replies.add(reply);
    }

    public void removeReply(ReplyEntity reply) {
        replies.remove(reply);
    }

    public void addFile(UploadFileEntity file) {
        getFiles().add(file);

        file.addBoard(this);
    }

    public void removeFile(UploadFileEntity file) {
        files.remove(file);
    }

}
