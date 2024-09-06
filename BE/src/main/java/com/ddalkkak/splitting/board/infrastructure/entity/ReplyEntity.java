package com.ddalkkak.splitting.board.infrastructure.entity;

import jakarta.persistence.*;

@Table(name="REPLY")
@Entity
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long subIdx;

    private String content;

    private String ip;

    private String id;

    private String pw;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

}
