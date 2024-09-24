package com.ddalkkak.splitting.board.api.response;

import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.dto.UploadFileDto;
import com.ddalkkak.splitting.comment.dto.CommentView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

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
        @Schema(description = "글 카테고리", example = "롤|정치|축구")
        String category,
        @Schema(description = "왼쪽 추천 수", example = "315")
        Long leftRecommend,
        @Schema(description = "오른쪽 추천 수", example = "200")
        Long rightRecommend,

        @Schema(description = "댓글")
        List<CommentResponse> comments,
        @Schema(description = "업로드 파일")
        List<UploadFileResponse> files

) {

    public static BoardDetailedResponse from(BoardDto boardDto){
        return BoardDetailedResponse.builder()
                .id(boardDto.id())
                .title(boardDto.title())
                .writer(boardDto.writer())
                .content(boardDto.content())
                .category(boardDto.category())
                .createDate(boardDto.createDate())
                .leftRecommend(boardDto.leftCnt())
                .rightRecommend(boardDto.rightCnt())
                .comments(boardDto.comments().stream()
                        .map(CommentResponse::from)
                        .collect(Collectors.toList()))
                .files(boardDto.files().stream().map(UploadFileResponse::from).collect(Collectors.toList()))
                .build();
    }
    @Builder
     record CommentResponse (

        @Schema(description = "댓글 ID", example = "1")
        Long id,
        @Schema(description = "부모 댓글 ID", example = "0")
        Long parentId,
         @Schema(description = "작성자", example = "임정환")
        String writer,
         @Schema(description = "내용", example = "100개 안에 성공한다.")
        String content,
        @Schema(description = "수정시간", example = "2024-09-07T14:58:02.714+00:00")
        String modifyDate
    ) {
        public static CommentResponse from(CommentView commentView){
            return CommentResponse.builder()
                    .id(commentView.id())
                    .writer(commentView.writer())
                    .content(commentView.content())
                    .modifyDate(commentView.createdAt())
                    .build();
        }

     }

    @Builder
    record UploadFileResponse(
            @Schema(description = "파일이름", example = "test.jpg")
            String fileName,
            @Schema(description = "파일 데이터(바이트)", example = "...")
            byte[] data
    ){
        public static UploadFileResponse from(UploadFileDto uploadFileDto){
            return UploadFileResponse.builder()
                    .fileName(uploadFileDto.fileName())
                    .data(uploadFileDto.data())
                    .build();
        }

    }
}
