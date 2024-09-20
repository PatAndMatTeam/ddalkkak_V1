package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
 public record UploadFileCreateDtoV1(
        List<MultipartFile> files,
        int width,
        int height) {

//    public static UploadFileCreateDtoV1 from(BoardCreateRequest.FileUploadRequest fileUploadRequest){
//        return UploadFileCreateDtoV1.builder()
//                .files(fileUploadRequest.files())
//                .width(fileUploadRequest.width())
//                .height(fileUploadRequest.height())
//                .build();
//    }

}
