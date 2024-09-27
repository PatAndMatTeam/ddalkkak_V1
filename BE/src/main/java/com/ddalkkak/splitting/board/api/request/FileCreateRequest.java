package com.ddalkkak.splitting.board.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class FileCreateRequest {
        @Schema(description = "업로드 대상 사진 파일")
        List<MultipartFile> files;

        @Schema(description = "사진 가로 보정 길이", example = "200")
        @Min(value = 0, message = "최소 값은 0 입니다.")
        int width = 200;

        @Schema(description = "사진 세로 보정 길이", example = "200")
        @Min(value = 0, message = "최소 값은 0 입니다.")
        int height = 200;
}