package com.ddalkkak.splitting.board.dto;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@NoArgsConstructor
public class UploadFileCreateDto{
                                        Long boardId;
                                        String fileTitle;
                                        String fileName;
                                         String fileType;
                                         byte[] data;
    @Builder
    public UploadFileCreateDto( Long boardId,
                                String fileTitle,
                                String fileName,
                                String fileType,
                                byte[] data){
        this.fileTitle = fileTitle;
        this.boardId = boardId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }


    public static UploadFileCreateDto of(String fileTitle, String fileName, String fileType, byte[] bytes){
        return UploadFileCreateDto.builder()
                .fileTitle(fileTitle)
                .fileName(fileName)
                .fileType(fileType)
                .data(bytes)
                .build();
    }

    public UploadFileEntity toEntity(){
        return UploadFileEntity.builder()
                .fileTile(this.fileTitle)
                .fileName(this.fileName)
                .fileType(this.fileType)
                .data(this.data)
                .build();
    }

}
