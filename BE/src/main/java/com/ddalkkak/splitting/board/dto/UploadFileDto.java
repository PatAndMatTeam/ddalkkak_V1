package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import lombok.Builder;

@Builder
public record UploadFileDto(
        String fileName,
        String fileType,
        byte[] data
) {

    public static UploadFileDto from(UploadFileEntity entity){
        return UploadFileDto.builder()
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .data(entity.getData())
                .build();
    }
}
