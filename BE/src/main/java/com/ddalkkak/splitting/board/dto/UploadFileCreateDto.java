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
                                        String fileName;
                                         String fileType;
                                         byte[] data;
    @Builder
    public UploadFileCreateDto( Long boardId,
                                String fileName,
                                String fileType,
                                byte[] data){
        this.boardId = boardId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }


    public static UploadFileCreateDto of(String fileName, String fileType, byte[] bytes){
        return UploadFileCreateDto.builder()
                .fileName(fileName)
                .fileType(fileType)
                .data(bytes)
                .build();
    }

    public UploadFileEntity toEntity(){
        return UploadFileEntity.builder()
                .fileName(this.fileName)
                .fileType(this.fileType)
                .data(this.data)
                .build();
    }

     public static UploadFileCreateDto fromMultipartFile(MultipartFile file) {
         try {
             return UploadFileCreateDto.builder()
                     .fileName(file.getOriginalFilename())
                     .fileType(file.getContentType())
                     .data(file.getBytes())  // 파일 데이터를 byte 배열로 변환
                     .build();
         } catch (IOException e) {
             throw new RuntimeException("파일 변환 중 오류가 발생했습니다.", e);
         }
     }


}
