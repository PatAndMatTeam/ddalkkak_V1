package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardPageableRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
import com.ddalkkak.splitting.board.service.BoardManager;
import com.ddalkkak.splitting.swagger.api.BoardApiDocs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
public class BoardApi implements BoardApiDocs {
    private final BoardManager boardManager;

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("id") long id){
        BoardDetailedResponse response = BoardDetailedResponse.from(boardManager.read(id));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BoardDetailedResponse>> getBoards(@RequestBody @Valid BoardPageableRequest pageableRequest) {

        List<BoardDetailedResponse> result = boardManager.readAll(pageableRequest).stream()
                .map(BoardDetailedResponse::from)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createBoard(@Valid @RequestBody BoardCreateRequest boardCreateRequest){
        log.info(boardCreateRequest.title());
        boardManager.create(boardCreateRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable("id") long id,
                    @RequestBody BoardUpdateRequest boardUpdateRequest){
        boardManager.update(id, boardUpdateRequest);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBoard(@PathVariable("id") long id){

        boardManager.delete(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
