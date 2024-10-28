package com.ddalkkak.splitting.comment.api;

import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentDeleteRequest;
import com.ddalkkak.splitting.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api/board/")
@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentApi implements CommentApiDocs {

    private final CommentService commentService;

    @PostMapping(value = "/{boardId}/comment", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void>  create(@PathVariable("boardId") long boardId,
                       @Valid @RequestBody CommentCreateRequest commentCreateRequest){

        commentService.create(boardId,commentCreateRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @DeleteMapping(value = "/{boardId}/comment/{commentId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void>  delete(@PathVariable("commentId") Long replyId,
                                        @RequestBody CommentDeleteRequest commentDeleteRequest){
        commentService.delete(replyId, commentDeleteRequest);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }


}
