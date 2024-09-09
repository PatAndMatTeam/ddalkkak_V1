package com.ddalkkak.splitting.board.infrastructure.entity;

import jakarta.persistence.*;

@Entity
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;
}
