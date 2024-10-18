package com.ddalkkak.splitting.board.api.response;

import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.dto.BoardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CategoryAnalysisBoardAllQueryResponse(
        BoardQueryResponse infos
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
            String modifyDate,
            @Schema(description = "카테고리", example = "롤|정치|축구")
            String category,
            @Schema(description = "추천수", example = "13")
            Long recommend,
            @Schema(description = "방문수", example = "13")
            Long visited,
            @Schema(description = "업로드 파일")
            List<BoardDetailedResponse.UploadFileResponse> files,

            List<BoardQueryResponse> childs
    ){

        public static BoardQueryResponse from(Board board){
            return BoardQueryResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .writer(board.getWriter())
                    .category(board.getCategory())
                    .recommend(board.getRecommend())
                    .visited(board.getVisited())
                    .modifyDate(board.getModifiedDate().toString())
                    .files(board.getFiles().stream()
                            .map(BoardDetailedResponse.UploadFileResponse::from)
                            .collect(Collectors.toList()))
                    .childs(board.getChildren().stream().map(BoardQueryResponse::from).collect(Collectors.toList()))
                    .build();
        }
    }
}
