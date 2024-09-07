package com.ddalkkak.splitting.board.infrastructure.entity;

import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

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


}
