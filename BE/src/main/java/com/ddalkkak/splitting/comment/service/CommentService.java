package com.ddalkkak.splitting.comment.service;

import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.exception.BoardErrorCode;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentDeleteRequest;
import com.ddalkkak.splitting.comment.domain.Comment;
import com.ddalkkak.splitting.comment.dto.CommentCreateDto;
import com.ddalkkak.splitting.comment.exception.CommentErrorCode;
import com.ddalkkak.splitting.comment.exception.CommentException;
import com.ddalkkak.splitting.comment.instrastructure.entitiy.CommentEntity;
import com.ddalkkak.splitting.comment.instrastructure.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentManager commentManager;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Long create(Long boardId, CommentCreateRequest commentCreateRequest) {
        BoardEntity board = boardRepository.findById(boardId).get();

        Comment create = Comment.from(boardId, commentCreateRequest);

        CommentEntity entity = CommentEntity.fromModel(create);
        entity.setBoard(board);


        return commentRepository.save(entity).getId();

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
