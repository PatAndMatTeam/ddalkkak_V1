package com.ddalkkak.splitting.board.infrastructure.entity;

import com.ddalkkak.splitting.board.domain.UploadFile;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="UP_FILE")
@Entity
public class UploadFileEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileTile;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 연관 관계의 주인 역할을 수행
    private BoardEntity board;

    public void addBoard(BoardEntity board){
        this.board = board;
    }


    public UploadFile toModel(){
        return UploadFile.builder()
                .id(this.id)
                .fileName(this.fileName)
                .fileTile(this.fileTile)
                .fileType(this.fileType)
                .data(this.data)
                .build();
    }

    public static UploadFileEntity fromModel(UploadFile uploadFile){
        return UploadFileEntity.builder()
                .fileName(uploadFile.getFileName())
                .fileTile(uploadFile.getFileTile())
                .fileType(uploadFile.getFileType())
                .data(uploadFile.getData())
                .build();
    }
}
