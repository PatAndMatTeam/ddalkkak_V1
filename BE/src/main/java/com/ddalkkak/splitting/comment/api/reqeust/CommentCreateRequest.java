package com.ddalkkak.splitting.comment.api.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CommentCreateRequest(
        @Schema(description = "작성자", example = "yjy")
        @NotBlank(message = "작성자를 입력해주세요")
        String writer,
        @Schema(description = "비밀번호", example = "1234")
        @NotBlank(message = "비밀번호를 입력해주세요")
        String password,
        @Schema(description = "댓글 내용", example = "ㅋㅋㅋㅋㅋㅋ")
        @NotBlank(message = "댓글을 입력해주세요")
        String content,
        @Schema(description = "부모 댓글 id", example = "0")
        Long parentId,

        @Schema(description = "댓글 위치는 왼쪽", example = "true|false")
        boolean isLeftPosition,

        @Schema(description = "댓글 위치는 오른쪽", example = "true|false")
        boolean isRightPosition
) {
}
