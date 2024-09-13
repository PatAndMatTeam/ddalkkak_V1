package com.ddalkkak.splitting.user.instrastructure.entity;

import com.ddalkkak.splitting.board.infrastructure.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Table(name="USERS")
@Entity
public class UserEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String userPw;

    private String ip;
}
