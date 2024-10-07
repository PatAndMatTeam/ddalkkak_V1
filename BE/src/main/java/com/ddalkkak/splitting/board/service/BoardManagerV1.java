package com.ddalkkak.splitting.board.service;


import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.exception.BoardErrorCode;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
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
class BoardManagerV1 {

    private final BoardRepository boardRepository;
    private final FileManager fileManager;



    @Transactional
    public Long create(Board board){
        BoardEntity entity = BoardEntity.fromModel(board);

        try{
            entity.getFiles().stream()
                    .forEach(file -> {
                        entity.addFile(file);
                    });
        }catch (NullPointerException npe){
            log.info("파일이 없으므로 파일 등록은 제외합니다.");
        }
        return boardRepository.save(entity).getId();
    }


    @Transactional
    public Board read(Long id){
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id))
                .toModel();
    }

    public List<Board> readAll(int start, int end){
        Pageable pageable = PageRequest.of(start, end);
        return boardRepository.findAll(pageable).getContent()
                .stream()
                .map(BoardEntity::toModel)
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

        entity.getFiles().stream()
                        .forEach(file -> {
                            entity.removeFile(file);
                        });

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
