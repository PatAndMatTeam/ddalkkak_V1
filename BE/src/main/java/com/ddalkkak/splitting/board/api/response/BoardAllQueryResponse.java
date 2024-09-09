package com.ddalkkak.splitting.board.api.response;

import com.ddalkkak.splitting.board.dto.BoardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardAllQueryResponse(
        List<BoardQueryResponse> infos
) {
    @Builder
    public static record BoardQueryResponse(
            @Schema(description = "번호", example = "1")
            Long id,
            @Schema(description = "제목", example = "사이드 프로젝트 성공할까?")
            String title,
            @Schema(description = "작성자", example = "윤주영")
            String writer,
            @Schema(description = "수정 시간", example = "2024-09-07T14:58:02.714+00:00")
            String modifyDate){

        public static BoardQueryResponse from(BoardDto boardDto){
            return BoardQueryResponse.builder()
                    .id(boardDto.id())
                    .title(boardDto.title())
                    .writer(boardDto.writer())
                    .modifyDate(boardDto.createDate())
                    .build();
        }
    }
}
