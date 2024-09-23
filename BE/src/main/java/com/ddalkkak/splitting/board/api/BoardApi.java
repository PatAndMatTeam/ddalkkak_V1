package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileUploadRequest;
import com.ddalkkak.splitting.board.api.response.BoardAllQueryResponse;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.swagger.api.BoardApiDocs;
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

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("id") long id){
        BoardDetailedResponse response = BoardDetailedResponse.from(boardService.read(id));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/all")
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



    @PostMapping(path = "/" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBoard(@Valid @ModelAttribute BoardCreateRequest boardCreateRequest){

        boardService.create(boardCreateRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable("id") long id,
                    @RequestBody BoardUpdateRequest boardUpdateRequest){
        boardService.update(id, boardUpdateRequest);

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
}
