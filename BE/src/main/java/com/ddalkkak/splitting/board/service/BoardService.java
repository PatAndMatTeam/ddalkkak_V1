package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;

import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardManager boardManager;
    private final FileService fileService;

    public Long create(BoardCreateRequest createRequest) {
        try {
            //1. 변환
            BoardCreateDto board = BoardCreateDto
                    .from(createRequest);
            log.info("변환 {}", board);
            UploadFileCreateDto file = fileService.make(createRequest.uploadFile());
            log.info("변환 {}", file);
            board.addFiles(file);

            return boardManager.create(board);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return 0L;
    }

    public BoardDto read(Long id){
        BoardDto entity = boardManager.read(id);
        //lazi loading
        return entity;
    }

    public List<BoardDto> readAll(int start, int end){
        return boardManager.readAll(start, end);
    }

    public void update(Long id, BoardUpdateRequest updateRequest){
       boardManager.update(id, updateRequest);
    }

    public void delete(Long id){
        boardManager.delete(id);
    }


}
