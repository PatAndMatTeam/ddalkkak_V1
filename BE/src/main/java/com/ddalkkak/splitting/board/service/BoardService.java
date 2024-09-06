package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    public void create(BoardCreateRequest createRequest){
        boardRepository.save(
                BoardEntity.from(BoardCreateDto
                        .from(createRequest)));
    }

    public BoardDto read(Long id){
        return BoardDto.from(boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾을 수 없음")));
    }
}
