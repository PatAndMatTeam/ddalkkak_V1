package com.ddalkkak.splitting.board.api;


import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.api.response.BoardAllQueryResponse;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
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
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/board/v2")
@RestController
@RequiredArgsConstructor
@Validated
public class BoardApiV2 implements BoardApiDocsV2 {
/*
/api/board/{롤}/all
/api/board/롤/{id}/all
/api/board/롤/{id}/{aId}*/
    private final BoardService boardService;


    @GetMapping(path = "/{category}/{categoryBoardId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("category") String category,
                                                            @PathVariable("categoryBoardId") long parentId){
        //1.아티클
        //2.하위 분석글
        BoardDetailedResponse response = BoardDetailedResponse.from(boardService.read(parentId));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }





    @GetMapping(path ="/{category}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> getBoards(@PathVariable("category") String category,
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
    public ResponseEntity<Void> createParent(@Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                             @Valid @RequestPart(value = "fileInfo", required = false) List<FileCreateRequest> fileInfoRequest){

        log.info("z");
        boardService.create(boardCreateRequest, files, fileInfoRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    //  /api/board/롤/{id}/
    @PostMapping(path = "/{category}/{categoryBoardId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createChild(@PathVariable("categoryBoardId") Long parentId,
                                       @Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files){

        boardService.create(parentId,boardCreateRequest, files);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping(path = "/{category}/{categoryBoardId}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> getBoardAll(@PathVariable("category") String category,
                                                             @PathVariable("categoryBoardId") Long parentId,
                                                             @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                             @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end){
        //1.아티클
        //2.하위 분석글
        List<BoardAllQueryResponse.BoardQueryResponse> changeInfos =  boardService.readAll(parentId, category,start,end).stream()
                .map(BoardAllQueryResponse.BoardQueryResponse::from)
                .collect(Collectors.toList());

        BoardAllQueryResponse response = BoardAllQueryResponse.builder()
                .infos(changeInfos)
                .build();

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = "/{category}/{categoryBoardId}/{analysisBoardId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getAnalysisBoard(@PathVariable("categoryBoardId") long parentId,
                                                                  @PathVariable("analysisBoardId") long childId){
        //1.아티클
        //2.하위 분석글

        return new ResponseEntity<>(null, HttpStatus.FOUND);
    }
}
