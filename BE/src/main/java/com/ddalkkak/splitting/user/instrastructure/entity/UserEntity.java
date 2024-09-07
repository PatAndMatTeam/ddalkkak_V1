package com.ddalkkak.splitting.user.instrastructure.entity;

import com.ddalkkak.splitting.board.infrastructure.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Table(name="USER")
@Entity
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String id;

    private String pw;

    private String ip;
}
