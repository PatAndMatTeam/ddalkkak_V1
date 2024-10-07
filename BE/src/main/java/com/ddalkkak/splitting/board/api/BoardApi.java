package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.api.response.BoardAllQueryResponse;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
import com.ddalkkak.splitting.board.api.response.BoardRecommendResponse;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.board.service.BoardServiceV1;
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
public class BoardApi implements BoardApiDocs {
    private final BoardService boardService;
    private final BoardServiceV1 boardServiceV1;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("id") long id){
        BoardDetailedResponse response = BoardDetailedResponse.from(boardService.read(id));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path ="/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> getBoards(@RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                             @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end) {
        List<BoardAllQueryResponse.BoardQueryResponse> changeInfos =  boardService.readAll(start, end).stream()
                .map(BoardAllQueryResponse.BoardQueryResponse::from)
                .collect(Collectors.toList());

        BoardAllQueryResponse response = BoardAllQueryResponse.builder()
                .infos(changeInfos)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }



    @PostMapping(path = "/" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createBoard(@Valid @RequestPart(value="board") BoardCreateRequest boardCreateRequest,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                              @Valid @RequestPart(value = "fileInfo", required = false) List<FileCreateRequest> fileInfoRequest){

        log.info("{}", fileInfoRequest);
        boardService.create(boardCreateRequest, files, fileInfoRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


//    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<Void> updateBoard(@PathVariable("id") long id,
//                    @RequestBody BoardUpdateRequest boardUpdateRequest){
//        boardService.update(id, boardUpdateRequest);
//
//        return ResponseEntity
//                .status(HttpStatus.ACCEPTED)
//                .build();
//    }

    @PatchMapping(value = "/V1/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateBoardV1(@PathVariable("id") long id,
                                            @RequestPart BoardUpdateRequest boardUpdateRequest,
                                              @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                              @Valid @RequestPart(value = "fileInfo", required = false) List<FileCreateRequest> fileInfoRequest){
        boardServiceV1.update(id, boardUpdateRequest, files, fileInfoRequest);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBoard(@PathVariable("id") long id){

        boardService.delete(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }


    @PatchMapping(path="/{id}/recommend")
    public ResponseEntity<BoardRecommendResponse> recommend(@PathVariable("id") long id,
                                                            @Valid @RequestBody BoardRecommendUpdateRequest boardRecommendUpdateRequest){
        BoardRecommendResponse response =
                BoardRecommendResponse.from(boardService.update(id, boardRecommendUpdateRequest)) ;


        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PatchMapping(path="/{id}/visit")
    public ResponseEntity<BoardRecommendResponse> visit(@PathVariable("id") long id){
        boardServiceV1.visit(id);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @GetMapping(path ="/{category}/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardAllQueryResponse> getBoards(@PathVariable("category") String category,
                                                            @RequestParam(value = "start", defaultValue = "0")@Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer start,
                                                           @RequestParam(value = "end", defaultValue = "10")  @Min(value = 0, message = "start 값은 0보다 크거나 같아야 합니다.") Integer end) {
        List<BoardAllQueryResponse.BoardQueryResponse> changeInfos =  boardServiceV1.readAll(category,start, end).stream()
                .map(BoardAllQueryResponse.BoardQueryResponse::from)
                .collect(Collectors.toList());

        BoardAllQueryResponse response = BoardAllQueryResponse.builder()
                .infos(changeInfos)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }



}
