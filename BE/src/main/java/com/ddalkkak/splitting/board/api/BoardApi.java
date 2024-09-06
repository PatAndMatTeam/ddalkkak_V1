package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.swagger.api.BoardApiDocs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/board")
@RestController
@RequiredArgsConstructor
public class BoardApi implements BoardApiDocs {
    private final BoardService boardService;

    @GetMapping("/")
    public void getBoard(){

    }

    @PostMapping("/")
    public void createBoard(@RequestBody BoardCreateRequest boardCreateRequest){
        log.info(boardCreateRequest.title());
        boardService.create(boardCreateRequest);
    }

    @GetMapping("/{nums}")
    public void getBoards(@PathVariable("num") long num){

    }



    @PatchMapping("/{num}")
    public void updateBoard(@PathVariable("num") long num,
                    @RequestBody BoardUpdateRequest boardUpdateRequest){

    }

    @DeleteMapping("/{num}")
    public void removeBoard(@PathVariable("num") long num){

    }
}
