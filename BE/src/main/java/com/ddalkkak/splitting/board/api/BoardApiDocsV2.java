package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileInfoCreateRequest;
import com.ddalkkak.splitting.board.api.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

@Tag(name = "카테고리/분석 글 작성 API V2", description = "카테고리/분석 글 작성 API 입니다.")
public interface BoardApiDocsV2 {

    @Operation(summary = "카테고리 글 상세확인", description = "카테고리 글 번호로 카테고리 글 상세확인.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "카테고리 글 상세확인 성공",
                    content = @Content(schema = @Schema(implementation = CategoryBoardDetailedResponse.class)))
    })
    @GetMapping(path = "/{category}/{categoryBoardId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CategoryBoardDetailedResponse> getCategoryBoard(@PathVariable("category") String category,
                                                                          @PathVariable("categoryBoardId") long parentId);


    @Operation(summary = "카테고리 글 리스트 조회", description = "카테고리 글 리스트를 조회합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "start", description = "시작 번호(0 부터)", example = "0")
    @Parameter(name = "end", description = "종료 번호", example = "2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "카테고리 글 조회 성공",
                    content = @Content(schema = @Schema(implementation = BoardAllQueryResponse.class))),
    })
    @GetMapping(path ="/{category}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> getCategoryBoardAll(@PathVariable("category") String category,
                                                           @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                           @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end);


    @Operation(summary = "카테고리 글 생성", description = "카테고리 글을 생성합니다.")
    @ArraySchema(schema = @Schema(implementation = FileInfoCreateRequest.class, description = "스웨거 버그 있음 처음에 생기는 배열 문자 [] 이거 없애고 다시 추가해서 하면 잘됨", example = "[{\"fileTitle\":\"나루토\",\"width\":200,\"height\":200}]"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 글 생성 성공"),
            @ApiResponse(responseCode = "403", description = "카테고리 글 생성 실패")
    })
    @PostMapping(path = "/{category}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createCategoryBoard(
                                            @Parameter(name = "category", description = "카테고리 명", example = "lol")
                                            @PathVariable("category") String category,
                                            @Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                             @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                            @ArraySchema(schema = @Schema(implementation = FileInfoCreateRequest.class, example = "[{\"fileTitle\":\"나루토\",\"width\":200,\"height\":200}]"))
                                            @Valid @RequestPart(value = "fileInfo", required = false) List<FileInfoCreateRequest> fileInfoRequest);


    @Operation(summary = "카테고리 글 수정", description = "카테고리 글 수정 합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "카테고리 글 수정 성공"),
    })
    @PatchMapping(value = "/{category}/{categoryBoardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateCategoryBoard(@PathVariable("analysisBoardId") long id,
                                                    @RequestPart(value="board") BoardUpdateRequest boardUpdateRequest,
                                                    @RequestPart(value = "files", required = false) Optional<List<MultipartFile>> files,
                                                    @ArraySchema(schema = @Schema(implementation = FileInfoCreateRequest.class, example = "[{\"fileTitle\":\"나루토\",\"width\":200,\"height\":200}]"))
                                                    @Valid @RequestPart(value = "fileInfo", required = false) List<FileInfoCreateRequest> fileInfoRequest);

    @Operation(summary = "카테고리 글 삭제", description = "카테고리 글 삭제 합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "카테고리 글 삭제 성공"),
    })
    @DeleteMapping(path = "/{category}/{categoryBoardId}")
    public ResponseEntity<Void> deleteCategoryBoard(@PathVariable("categoryBoardId") Long id);

    @Operation(summary = "분석 글 생성", description = "분석 글을 생성합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 분석 글 생성 성공"),
            @ApiResponse(responseCode = "403", description = "카테고리 분석 글 생성 실패")
    })
    @PostMapping(path = "/{category}/{categoryBoardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createAnalysisBoard(@PathVariable("categoryBoardId") Long parentId,
                                            @Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                            @RequestPart(value = "files", required = false) List<MultipartFile> files);


    @Operation(summary = "분석 글 조회", description = "분석 글 조회합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @Parameter(name = "analysisBoardId", description = "분석 글 번호", example = "7")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 분석 글 조회 성공",
                    content = @Content(schema = @Schema(implementation = BoardAllQueryResponse.class))),
    })
    @GetMapping(path = "/{category}/{categoryBoardId}/{analysisBoardId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getAnalysisBoard(@PathVariable("categoryBoardId") long parentId,
                                                                  @PathVariable("analysisBoardId") long childId);

    @Operation(summary = "분석 글 삭제", description = "분석 글 삭제 합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @Parameter(name = "analysisBoardId", description = "분석 글 번호", example = "7")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "카테고리 글 삭제 성공"),
    })
    @DeleteMapping(path = "/{category}/{categoryBoardId}/{analysisBoardId}")
    public ResponseEntity<Void> deleteAnalysisBoard(@PathVariable("analysisBoardId") Long id);


    @Operation(summary = "분석 글 수정", description = "분석 글 수정 합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @Parameter(name = "analysisBoardId", description = "분석 글 번호", example = "7")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "카테고리 글 수정 성공"),
    })
    @PatchMapping(value = "/{category}/{categoryBoardId}/{analysisBoardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateAnalysisBoard(@PathVariable("analysisBoardId") long id,
                                                    @RequestPart(value="board") BoardUpdateRequest boardUpdateRequest,
                                                    @RequestPart(value = "files", required = false) Optional<List<MultipartFile>> files,
                                                    @ArraySchema(schema = @Schema(implementation = FileInfoCreateRequest.class, example = "[{\"fileTitle\":\"나루토\",\"width\":200,\"height\":200}]"))
                                                    @Valid @RequestPart(value = "fileInfo", required = false) List<FileInfoCreateRequest> fileInfoRequest);

    @Operation(summary = "카테고리 분석 글 리스트 조회", description = "카테고리 분석 글 리스트를 조회합니다.")
    @Parameter(name = "category", description = "카테고리 명", example = "lol")
    @Parameter(name = "categoryBoardId", description = "카테고리 글 번호", example = "1")
    @Parameter(name = "start", description = "시작 번호(0 부터)", example = "0")
    @Parameter(name = "end", description = "종료 번호", example = "2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "카테고리 분석 글 리스트 조회 성공",
                    content = @Content(schema = @Schema(implementation = BoardAllQueryResponse.class))),
    })
    @GetMapping(path = "/{category}/{categoryBoardId}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CategoryAnalysisBoardAllQueryResponse> getAnalysisBoardAll(@PathVariable("category") String category,
                                                                                     @PathVariable("categoryBoardId") Long parentId,
                                                                                     @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                                                     @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end);

}
