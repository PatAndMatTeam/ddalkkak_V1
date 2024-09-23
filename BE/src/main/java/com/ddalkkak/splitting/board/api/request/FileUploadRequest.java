package com.ddalkkak.splitting.board.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record FileUploadRequest (
        @Schema(description = "업로드 대상 사진 파일", type = "string", format = "binary", example = "sample.jpg")
        List<MultipartFile> files,

        @Schema(description = "사진 가로 길이", example = "200")
        int width,
        @Schema(description = "사진 세로 길이", example = "200")
        int height
) {}