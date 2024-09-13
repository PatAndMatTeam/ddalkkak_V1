package com.ddalkkak.splitting.reply.instrastructure.entitiy;

import com.ddalkkak.splitting.board.infrastructure.entity.BaseTimeEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import jakarta.persistence.*;

@Table(name="REPLY")
@Entity
public class ReplyEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String ip;

    private String userId;

    private String userPw;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 연관 관계의 주인 역할을 수행
    private BoardEntity board;

}
