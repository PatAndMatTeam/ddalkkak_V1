package com.ddalkkak.splitting.board.api.response;

import com.ddalkkak.splitting.board.dto.BoardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardDetailedResponse(
        @Schema(description = "글 번호", example = "1")
        Long id,
        @Schema(description = "제목", example = "제목입니다.")
        String title,
        @Schema(description = "작성자", example = "윤주영")
        String writer,
        @Schema(description = "작성자", example = "갈라치기 사이트 성공할까?")
        String content,
        @Schema(description = "작성자", example = "2024-09-07T14:58:02.714+00:00")
        String createDate,

        List<Reply> replys
) {

    public static BoardDetailedResponse from(BoardDto boardDto){
        return BoardDetailedResponse.builder()
                .id(boardDto.id())
                .title(boardDto.title())
                .writer(boardDto.writer())
                .content(boardDto.content())
                .createDate(boardDto.createDate())
                .build();
    }
     record Reply (
         @Schema(description = "작성자", example = "임정환")
        String writer,
         @Schema(description = "내용", example = "100개 안에 성공한다.")
        String content,
        @Schema(description = "수정시간", example = "2024-09-07T14:58:02.714+00:00")
        String modifyDate
    ) {}
}
