package com.ddalkkak.splitting.board.api.response;

import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.dto.BoardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record BoardRecommendResponse(
        @Schema(description = "왼쪽 추천 수", example = "1")
        long leftVote,

        @Schema(description = "오른쪽 추천 수", example = "6")
        long rightVote

) {

    public static BoardRecommendResponse from(BoardDto boardDto){
        return BoardRecommendResponse.builder()
                .leftVote(boardDto.leftVote())
                .rightVote(boardDto.rightVote())
                .build();
    }

    public static BoardRecommendResponse from(Board board){
        return BoardRecommendResponse.builder()
                .leftVote(board.getLeftCnt())
                .rightVote(board.getRightCnt())
                .build();
    }
}
