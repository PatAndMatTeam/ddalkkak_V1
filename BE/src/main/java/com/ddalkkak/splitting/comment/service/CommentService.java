package com.ddalkkak.splitting.comment.service;

import com.ddalkkak.splitting.board.exception.BoardErrorCode;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentDeleteRequest;
import com.ddalkkak.splitting.comment.dto.CommentCreateDto;
import com.ddalkkak.splitting.comment.exception.CommentErrorCode;
import com.ddalkkak.splitting.comment.exception.CommentException;
import com.ddalkkak.splitting.comment.instrastructure.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentManager commentManager;

    public Long create(Long boardId, CommentCreateRequest commentCreateRequest) {
        return commentManager.create(boardId, commentCreateRequest);
    }

    @Transactional
    public void delete(Long id, CommentDeleteRequest commentDeleteRequest){
        String requestPassword = commentDeleteRequest.password();
        String password = commentManager.read(id).getPassword();

        if (!requestPassword.equals(password)){
            throw new CommentException.NotMatchPasswordException(CommentErrorCode.NOT_MATCH_PASSWORD, id);
        }

        commentManager.delete(id);
    }
}
