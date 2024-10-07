package com.ddalkkak.splitting.swagger.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.response.BoardAllQueryResponse;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentDeleteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API", description = "댓글 관련 API 입니다.")
public interface CommentApiDocs {
    
    @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
    @Parameter(name = "boardId", description = "글 번호", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "403", description = "댓글 생성 실패")
    })
    public ResponseEntity<Void>  create(@PathVariable("boardId") long boardId,
                       @RequestBody CommentCreateRequest commentCreateRequest);


    @Operation(summary = "댓글 삭제", description = "글 삭제 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "글 삭제 성공"),
    })
    public ResponseEntity<Void>  delete(@PathVariable("replyId") Long replyId,
                                        @RequestBody CommentDeleteRequest commentDeleteRequest);

}
