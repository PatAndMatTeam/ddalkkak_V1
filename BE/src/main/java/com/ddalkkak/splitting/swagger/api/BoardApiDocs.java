package com.ddalkkak.splitting.swagger.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardPageableRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
import com.ddalkkak.splitting.board.exception.BoardErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Board API", description = "Board 관련 API 입니다.")
public interface BoardApiDocs {


    @Operation(summary = "글 상세확인", description = "글 번호로 글 상세확인.")
    @Parameter(name = "id", description = "글의 번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "글 상세확인 성공",
                    content = @Content(schema = @Schema(implementation = BoardDetailedResponse.class)))
    })
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("id") long id);


    @Operation(summary = "글 생성", description = "글을 생성합니다.")
    @Parameter(name = "category", description = "글의 카테고리", example = "정치|축구|롤|애니")
    @Parameter(name = "title", description = "글 제목", example = "이직 vs 사업 뭐가 맞냐?")
    @Parameter(name = "content", description = "글 내용", example = "인생 힘들다")
    @Parameter(name = "writer", description = "글 작성자", example = "윤주영")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "글 생성 성공"),
    })
    public ResponseEntity<Void> createBoard(@RequestBody BoardCreateRequest boardCreateRequest);

    @Operation(summary = "글 리스트 조회", description = "글 리스트를 조회합니다.")
    @Parameter(name = "start", description = "시작 번호", example = "1")
    @RequestPa
    @Parameter(name = "end", description = "종료 번호", example = "2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "글 생성 성공",
                    content = @Content(schema = @Schema(implementation = BoardDetailedResponse.class))),
    })
    public ResponseEntity<List<BoardDetailedResponse>> getBoards(@RequestBody BoardPageableRequest pageableRequest);

    @Operation(summary = "글 수정", description = "글 수정 합니다.")
    @Parameter(name = "title", description = "제목", example = "나 이제 사업 할란다~")
    @Parameter(name = "content", description = "내용", example = "사업 아이템 추천좀")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "글 수정 성공",
                    content = @Content(schema = @Schema(implementation = BoardDetailedResponse.class))),
    })
    public ResponseEntity<Void> updateBoard(@PathVariable("id") long id,
                            @RequestBody BoardUpdateRequest boardUpdateRequest);


    @Operation(summary = "글 삭제", description = "글 삭제 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "글 삭제 성공",
                    content = @Content(schema = @Schema(implementation = BoardDetailedResponse.class))),
    })
    public ResponseEntity<Void> removeBoard(@PathVariable("id") long id);


}
