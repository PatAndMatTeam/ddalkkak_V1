package com.ddalkkak.splitting.board.infrastructure.entity;

import jakarta.persistence.*;

@Table(name="USER")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    private String id;

    private String pw;

    private String ip;
}
