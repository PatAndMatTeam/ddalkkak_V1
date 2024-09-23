package com.ddalkkak.splitting.comment.service;

import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.dto.CommentCreateDto;
import com.ddalkkak.splitting.comment.dto.CommentDto;
import com.ddalkkak.splitting.comment.instrastructure.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentManager {

    private final CommentRepository commentRepository;

    public CommentDto read(Long id){
        return CommentDto.from(commentRepository.findById(id)
                .get());
    }

    public Long create(Long boardId, CommentCreateRequest commentCreateRequest) {
        CommentCreateDto createDto = CommentCreateDto.from(boardId, commentCreateRequest);
        return commentRepository.save(createDto.toEntity()).getId();
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
