package com.ddalkkak.splitting.board.api;


import com.ddalkkak.splitting.board.api.response.BoardAllQueryResponse;
import com.ddalkkak.splitting.board.api.response.BoardDetailedResponse;
import com.ddalkkak.splitting.board.service.BoardService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
@Validated
public class CategoryArticleApi {

    private final BoardService boardService;


    @GetMapping(path = "/{category}/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BoardDetailedResponse> getBoard(@PathVariable("id") long id){
        //1.아티클
        //2.하위 분석글
        
        BoardDetailedResponse response = BoardDetailedResponse.from(boardService.read(id));

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping(path = "/{category}/{boardId}/{analysisId}", produces = {MediaType.APPLICATION_JSON_VALUE})
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
        List<BoardAllQueryResponse.BoardQueryResponse> changeInfos =  boardService.readAll(category,start, end).stream()
                .map(BoardAllQueryResponse.BoardQueryResponse::from)
                .collect(Collectors.toList());

        BoardAllQueryResponse response = BoardAllQueryResponse.builder()
                .infos(changeInfos)
                .build();
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
