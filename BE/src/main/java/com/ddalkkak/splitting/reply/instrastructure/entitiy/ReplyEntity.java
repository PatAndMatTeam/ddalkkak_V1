package com.ddalkkak.splitting.reply.instrastructure.entitiy;

import com.ddalkkak.splitting.board.infrastructure.entity.BaseTimeEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import jakarta.persistence.*;

@Table(name="REPLY")
@Entity
public class ReplyEntity extends BaseTimeEntity {

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
