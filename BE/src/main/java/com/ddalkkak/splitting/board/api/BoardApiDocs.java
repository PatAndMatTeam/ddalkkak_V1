package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.api.response.BoardAllQueryResponse;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
import com.ddalkkak.splitting.board.api.response.BoardRecommendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Tag(name = "글 API", description = "글 관련 API 입니다.")
public interface BoardApiDocs {


    @Operation(summary = "글 상세확인", description = "글 번호로 글 상세확인.")
    @Parameter(name = "id", description = "글의 번호(기본값: 1)", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "글 상세확인 성공",
                    content = @Content(schema = @Schema(implementation = BoardDetailedResponse.class)))
    })
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("id") long id);


    @Operation(summary = "글 리스트 조회", description = "글 리스트를 조회합니다.")
    @Parameter(name = "start", description = "시작 번호(0 부터)", example = "0")
    @Parameter(name = "end", description = "종료 번호", example = "2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "글 생성 성공",
                    content = @Content(schema = @Schema(implementation = BoardAllQueryResponse.class))),
    })
    public ResponseEntity<BoardAllQueryResponse> getBoards(@RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                           @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end);



    @Operation(summary = "글 생성 V1", description = "글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "글 생성 성공"),
            @ApiResponse(responseCode = "403", description = "글 생성 실패")
    })
    public ResponseEntity<Void> createBoard(@Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                            @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                            @Valid @RequestPart(value = "fileInfo", required = false) List<FileCreateRequest> fileInfoRequest);



    @Operation(summary = "글 수정", description = "글 수정 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "글 수정 성공"),
    })
    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateBoardV1(@PathVariable("id") long id,
                                              @RequestPart BoardUpdateRequest boardUpdateRequest,
                                              @RequestPart(value = "files", required = false) Optional<List<MultipartFile>> files,
                                              @Valid @RequestPart(value = "fileInfo", required = false) List<FileCreateRequest> fileInfoRequest);


    @Operation(summary = "글 삭제", description = "글 삭제 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "글 삭제 성공"),
    })
    public ResponseEntity<Void> removeBoard(@PathVariable("id") long id);


    @Operation(summary = "조회수 증가", description = "조회수 증가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "조회수 증가 성공"),
    })
    @PatchMapping(path="/{id}/visit")
    public ResponseEntity<BoardRecommendResponse> visit(@PathVariable("id") long id);

    @Operation(summary = "카테고리 별 글 리스트 조회", description = "카테고리 별 글 리스트 조회.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "글 리스트 조회 성공"),
    })
    @GetMapping(path ="/{category}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> getBoards(@PathVariable("category") String category,
                                                           @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                           @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end);

}
