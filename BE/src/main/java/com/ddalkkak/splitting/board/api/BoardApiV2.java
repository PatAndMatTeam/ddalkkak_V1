package com.ddalkkak.splitting.board.api;


import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileInfoCreateRequest;
import com.ddalkkak.splitting.board.api.response.*;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.service.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/board/v2")
@RestController
@RequiredArgsConstructor
@Validated
public class BoardApiV2 {
/*
/api/board/{롤}/all
/api/board/롤/{id}/all
/api/board/롤/{id}/{aId}*/
    private final BoardService boardService;


    @GetMapping(path = "/{category}/{categoryBoardId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CategoryBoardDetailedResponse> getCategoryBoardDetailed(@PathVariable("category") String category,
                                                                          @PathVariable("categoryBoardId") long parentId)
    {
        //1.아티클
        //2.하위 분석글
        CategoryBoardDetailedResponse response = CategoryBoardDetailedResponse.from(boardService.read(parentId));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(value = "/{category}/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> search(@PathVariable("category") String category,
                                                        @RequestParam(value = "title") String title,
                                                        @RequestParam(value = "content") String content,
                                                        @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                        @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end) {

        List<BoardAllQueryResponse.BoardQueryResponse> changeInfos = boardService.search(category, title, content, start, end)
                .stream()
                .map(BoardAllQueryResponse.BoardQueryResponse::from)
                .collect(Collectors.toList());

        BoardAllQueryResponse response = BoardAllQueryResponse.builder()
                .infos(changeInfos)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path ="/{category}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> getCategoryBoardAll(@PathVariable("category") String category,
                                                           @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                           @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end) {
        List<BoardAllQueryResponse.BoardQueryResponse> changeInfos =  boardService.readAll(category,start,end).stream()
                .map(BoardAllQueryResponse.BoardQueryResponse::from)
                .collect(Collectors.toList());

        BoardAllQueryResponse response = BoardAllQueryResponse.builder()
                .infos(changeInfos)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }


    @PostMapping(path = "/{category}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> createCategoryBoard(
                                    @PathVariable("category") String category,
                                    @Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                   @RequestPart(value = "files", required = false) Optional<List<MultipartFile>> files,
                                     @Valid @RequestPart(value = "fileInfo", required = false) Optional<List<FileInfoCreateRequest>> fileInfoRequest){

        Long createId = boardService.create(boardCreateRequest, files, fileInfoRequest);

        return new ResponseEntity<>(createId, HttpStatus.CREATED);
    }


    @DeleteMapping(path = "/{category}/{categoryBoardId}")
    public ResponseEntity<Void> deleteCategoryBoard(@PathVariable("categoryBoardId") Long id){

        boardService.delete(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @PatchMapping(value = "/{category}/{categoryBoardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Long> updateCategoryBoard(@PathVariable("categoryBoardId") long id,
                                              @RequestPart(value="board") BoardUpdateRequest boardUpdateRequest,
                                              @RequestPart(value = "files", required = false) Optional<List<MultipartFile>> files,
                                              @Valid @RequestPart(value = "fileInfo", required = false) Optional<List<FileInfoCreateRequest>> fileInfoRequest){
        boardService.update(id, boardUpdateRequest, files, fileInfoRequest);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }


    //  /api/board/롤/{id}/
    @PostMapping(path = "/{category}/{categoryBoardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> createAnalysisBoard(@PathVariable("categoryBoardId") Long parentId,
                                       @Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                       @RequestPart(value = "files", required = false) Optional<List<MultipartFile>> files){

        Long createId = boardService.create(parentId,boardCreateRequest, files);

        return new ResponseEntity<>(createId, HttpStatus.CREATED);
    }


    @GetMapping(path = "/{category}/{categoryBoardId}/{analysisBoardId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getAnalysisBoard(@PathVariable("categoryBoardId") long parentId,
                                                                  @PathVariable("analysisBoardId") long childId){
        //1.아티클
        //2.하위 분석글
        BoardDetailedResponse response = BoardDetailedResponse.from(boardService.read(childId));
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @DeleteMapping(path = "/{category}/{categoryBoardId}/{analysisBoardId}")
    public ResponseEntity<Void> deleteAnalysisBoard(@PathVariable("analysisBoardId") Long id){

        boardService.delete(id);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @PatchMapping(value = "/{category}/{categoryBoardId}/{analysisBoardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateAnalysisBoard(@PathVariable("analysisBoardId") long id,
                                              @RequestPart(value="board") BoardUpdateRequest boardUpdateRequest,
                                              @RequestPart(value = "files", required = false) Optional<List<MultipartFile>> files,
                                              @Valid @RequestPart(value = "fileInfo", required = false) Optional<List<FileInfoCreateRequest>> fileInfoRequest){
        boardService.update(id, boardUpdateRequest, files, fileInfoRequest);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }


    @GetMapping(path = "/{category}/{categoryBoardId}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CategoryAnalysisBoardAllQueryResponse> getAnalysisBoardAll(@PathVariable("category") String category,
                                                             @PathVariable("categoryBoardId") Long parentId,
                                                             @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                             @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end){
        //1.아티클
        //2.하위 분석글
        Board board = boardService.readAll(parentId, category,start,end);

        CategoryAnalysisBoardAllQueryResponse response = CategoryAnalysisBoardAllQueryResponse.builder()
                .infos(CategoryAnalysisBoardAllQueryResponse.BoardQueryResponse.from(board))
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = "/{category}/{categoryBoardId}/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CategoryBoardSearchResponse> searchAnalysisBoard(@PathVariable("category") String category,
                                                                           @PathVariable("categoryBoardId") Long parentId,
                                                                           @RequestParam("title") String title,
                                                                           @RequestParam("content") String content,
                                                                           @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                                           @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end){
        //1.아티클
        //2.하위 분석글
        List<CategoryBoardSearchResponse.BoardQueryResponse> infos = boardService.search(parentId, category, title, category,start,end)
                .stream()
                .map(CategoryBoardSearchResponse.BoardQueryResponse::from)
                .collect(Collectors.toList());

        CategoryBoardSearchResponse response = CategoryBoardSearchResponse.builder()
                .infos(infos)
                .build();

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }





}
