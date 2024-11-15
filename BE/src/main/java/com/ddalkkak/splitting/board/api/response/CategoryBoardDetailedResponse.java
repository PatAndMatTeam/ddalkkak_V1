package com.ddalkkak.splitting.board.api.response;

import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.dto.BoardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CategoryBoardDetailedResponse (
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
    @Schema(description = "글 카테고리", example = "롤|정치|축구")
    String category,
    @Schema(description = "글 추천 수", example = "123")
    Long recommend,
    @Schema(description = "왼쪽 추천 수", example = "315")
    Long leftRecommend,
    @Schema(description = "오른쪽 추천 수", example = "200")
    Long rightRecommend,
    @Schema(description = "글 방문수", example = "200")
    Long visited,
    @Schema(description = "댓글")
    List<BoardDetailedResponse.CommentResponse> comments,
    @Schema(description = "업로드 파일")
    List<BoardDetailedResponse.UploadFileResponse> files,
    @Schema(description = "분석글")
    List<AnalysisBoardDetailedResponse> analysisBoards


){
    public static CategoryBoardDetailedResponse from(Board board){
        return CategoryBoardDetailedResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .category(board.getCategory())
                .recommend(board.getRecommend())
                .visited(board.getVisited())
                .createDate(board.getModifiedDate().toString())
                .comments(board.getComments().stream()
                .map(BoardDetailedResponse.CommentResponse::from)
                        .collect(Collectors.toList()))
                .files(board.getFiles().stream()
                        .map(BoardDetailedResponse.UploadFileResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}


