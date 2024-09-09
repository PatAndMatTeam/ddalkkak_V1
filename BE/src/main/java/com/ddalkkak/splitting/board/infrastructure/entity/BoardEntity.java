package com.ddalkkak.splitting.board.infrastructure.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String writer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "board_id")  // Reply 테이블의 외래 키
    private List<ReplyEntity> replies = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private FileEntity file;

    public static BoardEntity from(BoardCreateDto dto){
        return BoardEntity.builder()
                .title(dto.title())
                .content(dto.content())
                .writer(dto.writer())
                .category(Category.valueOf(dto.category()))
                .build();
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    // Helper methods to manage replies
    public void addReply(ReplyEntity reply) {
        replies.add(reply);
    }

    public void removeReply(ReplyEntity reply) {
        replies.remove(reply);
    }

}
