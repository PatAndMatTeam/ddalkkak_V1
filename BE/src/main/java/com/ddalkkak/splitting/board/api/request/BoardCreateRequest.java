package com.ddalkkak.splitting.board.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 포함한 생성자 추가
@Builder
public class BoardCreateRequest {
    @Schema(description = "카테고리  value(politics|soccer|baseball|basketball|etc-sports|lol|starcraft|fps|rpg|mobile)|" +
            "etc-games|celebrity|girl-group|boy-group|cartoon|webtoon|character|fashion|health|movie|food|travel|pet|" +
            "music|internet|youtuber|vtuber", example = "lol")
    @NotBlank(message = "카테고리를 입력해주세요")
    @Pattern(regexp = "^[a-z]*$", message = "카테고리는 영어 알파벳 소문자로 입력해주세요.")
    String category;

    @Schema(description = "제목", example = "우리 성공할 수 있을까?")
    @NotBlank(message = "제목을 입력해주세요")
    String title;

    @Schema(description = "내용", example = "무조건 성공 해야지 어?")
    @NotBlank(message = "내용을 입력해주세요")
    String content;

    @Schema(description = "작성자", example = "윤주영")
    @NotBlank(message = "작성자를 입력해주세요")
    String writer;

    @Schema(description = "사진 가로 보정 길이", example = "200")
    @Min(value = 0, message = "최소 값은 0 입니다.")
    int width;

    @Schema(description = "사진 세로 보정 길이", example = "200")
    @Min(value = 0, message = "최소 값은 0 입니다.")
    int height;

}
