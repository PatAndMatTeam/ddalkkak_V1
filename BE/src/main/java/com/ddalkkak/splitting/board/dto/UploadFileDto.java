package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
public record UploadFileDto(
        String fileTitle,
        String fileName,
        String fileType,
        byte[] data
) {

    public static UploadFileDto from(UploadFileEntity entity){
        return UploadFileDto.builder()
                .fileTitle(entity.getFileTile())
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .data(entity.getData())
                .build();
    }
}
