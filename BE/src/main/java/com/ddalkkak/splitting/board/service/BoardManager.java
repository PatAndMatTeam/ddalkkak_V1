package com.ddalkkak.splitting.board.service;


import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.exception.BoardErrorCode;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
class BoardManager {

    private final BoardRepository boardRepository;
    private final FileManager fileManager;

    @Transactional
    public Long create(BoardCreateDto boardCreatDto){

        BoardEntity boardEntity = boardCreatDto.toEntity();

        try{
            List<UploadFileCreateDto> createDtos= boardCreatDto.getFiles();

            createDtos.stream()
                    .forEach(x -> {
                        boardEntity.addFile(x.toEntity());
                    });
        }catch (NullPointerException npe){
            log.info("파일이 없으므로 파일 등록은 제외합니다.");
        }
        return boardRepository.save(boardEntity).getId();
    }

    @Transactional
    public BoardDto read(Long id){
        return BoardDto.from(boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id)));
    }

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

    @Transactional
    public void update(Long id, BoardRecommendUpdateRequest updateRequest){
        BoardEntity entity = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id));

        entity.changeLeftCnt(updateRequest.leftRecommend());
        entity.changeRightCnt(updateRequest.rightRecommend());
    }
}
