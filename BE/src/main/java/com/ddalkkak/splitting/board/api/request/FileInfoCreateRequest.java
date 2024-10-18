package com.ddalkkak.splitting.board.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfoCreateRequest {

            @Schema(description = "게시글에 표시되기 위한 사진의 제목", example = "나루토")
            String fileTitle;
            @Schema(description = "사진 가로 보정 길이", example = "200")
            @Min(value = 0, message = "최소 값은 0 입니다.")
            int width;
            @Schema(description = "사진 세로 보정 길이", example = "200")
            @Min(value = 0, message = "최소 값은 0 입니다.")
            int height;
}