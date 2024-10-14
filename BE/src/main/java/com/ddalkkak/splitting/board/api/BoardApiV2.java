package com.ddalkkak.splitting.board.api;


import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
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
@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
@Validated
public class BoardApiV2 {
/*
/api/board/{롤}/all
/api/board/롤/{id}/all
/api/board/롤/{id}/{aId}*/
    private final BoardService boardService;


    @GetMapping(path = "/{category}/{parentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("category") String category,
                                                            @PathVariable("id") long id){
        //1.아티클
        //2.하위 분석글
        BoardDetailedResponse response = BoardDetailedResponse.from(boardService.read(id));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = "/{category}/{parentId}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getBoardAll(@PathVariable("category") String category,
                                                          @PathVariable("id") long id){
        //1.아티클
        //2.하위 분석글
        BoardDetailedResponse response = BoardDetailedResponse.from(boardService.read(id));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = "/{category}/{parentId}/{childId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getAnalysisBoard(@PathVariable("boardId") long boardId,
                                                                  @PathVariable("analysisId") long analysisId){
        //1.아티클
        //2.하위 분석글

        return new ResponseEntity<>(null, HttpStatus.FOUND);
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


    //  /api/board/롤/{id}/{aId}
    @PostMapping(path = "/{category}/{parentId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> create(@PathVariable("boardId") Long id,
                                       @Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                       @RequestPart(value = "files", required = false) List<MultipartFile> files){

        boardService.create(id,boardCreateRequest, files);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
