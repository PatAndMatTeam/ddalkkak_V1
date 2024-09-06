package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardPatchRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/board")
@RestController
public class BoardApi {


    @GetMapping("/")
    public void getBoard(){

    }

    @GetMapping("/{nums}")
    public void getBoards(@PathVariable("num") long num){

    }

    @PatchMapping("/{num}")
    public void updateBoard(@PathVariable("num") long num,
                    @RequestBody BoardPatchRequest boardPatchRequest){

    }

    @DeleteMapping("/{num}")
    public void removeBoard(@PathVariable("num") long num){

    }
}
