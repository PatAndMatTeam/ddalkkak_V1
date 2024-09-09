package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardPageableRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.exception.BoardErrorCode;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardManager {

    private final BoardRepository boardRepository;


    public void create(BoardCreateRequest createRequest){
        boardRepository.save(
                BoardEntity.from(BoardCreateDto
                        .from(createRequest)));
    }

    public BoardDto read(Long id){
        return BoardDto.from(boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id)));
    }

//    public List<BoardDto> readAll(BoardPageableRequest pageableRequest){
//
//        Pageable pageable = PageRequest.of(pageableRequest.start(), pageableRequest.end());
//        return boardRepository.findAll(pageable).getContent()
//                .stream()
//                .map(BoardDto::from)
//                .collect(Collectors.toList());
//    }

    public List<BoardDto> readAll(int start, int end){

        Pageable pageable = PageRequest.of(start, end);
        return boardRepository.findAll(pageable).getContent()
                .stream()
                .map(BoardDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, BoardUpdateRequest updateRequest){
        BoardDto dto = BoardDto.from(updateRequest);
        BoardEntity entity = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id));

        entity.changeTitle(dto.title());
        entity.changeContent(dto.content());
    }
}
