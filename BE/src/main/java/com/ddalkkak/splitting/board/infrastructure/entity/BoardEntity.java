package com.ddalkkak.splitting.board.infrastructure.entity;

import jakarta.persistence.*;

@Table(name="BOARD")
@Entity
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    private String title;

    private String content;

    private Category category;

}
