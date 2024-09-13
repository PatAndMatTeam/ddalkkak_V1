package com.ddalkkak.splitting.reply.api;

import com.ddalkkak.splitting.reply.api.reqeust.ReplyCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/reply")
@Slf4j
@RequiredArgsConstructor
@RestController
public class ReplyApi {

    @GetMapping("/")
    public void getReplys(){

    }

    @PutMapping("/")
    public void createReply(@RequestBody ReplyCreateRequest replyCreateRequest){

    }


    @DeleteMapping("/{id}")
    public void deleteReply(@PathVariable("id") Long id){

    }
}
