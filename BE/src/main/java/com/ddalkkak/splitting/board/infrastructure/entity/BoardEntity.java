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
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String title;

    private String content;

    private Category category;

    private String writer;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    public static BoardEntity from(BoardCreateDto dto){
        return BoardEntity.builder()
                .title(dto.title())
                .content(dto.content())
                .writer(dto.writer())
                .category(Category.valueOf(dto.category()))
                .build();
    }


}
