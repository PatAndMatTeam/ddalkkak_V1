package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;

import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardManager boardManager;
    private final FileService fileService;

    public Long create(final BoardCreateRequest createRequest,
                       final List<MultipartFile> multipartFiles,
                         final List<FileCreateRequest> fileInfoRequest) {
        //1. 변환
        BoardCreateDto board = BoardCreateDto
                .from(createRequest);

        if (multipartFiles!=null){
            List<UploadFileCreateDto> fileCreateDtos = fileService.make(multipartFiles, fileInfoRequest);

            board.addFiles(fileCreateDtos);
        }

        return boardManager.create(board);
    }

    public Long createV1(final BoardCreateRequest createRequest,
                       final List<MultipartFile> multipartFiles,
                       final List<FileCreateRequest> fileInfoRequest) {
        //1. 변환
//        Board board = Board.from(createRequest, )

        BoardCreateDto board = BoardCreateDto
                .from(createRequest);

        if (multipartFiles!=null){
            List<UploadFileCreateDto> fileCreateDtos = fileService.make(multipartFiles, fileInfoRequest);

            board.addFiles(fileCreateDtos);
        }

        return boardManager.create(board);
    }

    public BoardDto read(Long id){
        BoardDto board = boardManager.read(id);
        //lazi loading
        log.info("{}:::", board);
        return board;
    }

    public List<BoardDto> readAll(int start, int end){
        return boardManager.readAll(start, end);
    }

    public void update(Long id, BoardUpdateRequest updateRequest){
       boardManager.update(id, updateRequest);
    }

    public BoardDto update(Long id, BoardRecommendUpdateRequest boardRecommendUpdateRequest){
        boardManager.update(id, boardRecommendUpdateRequest);

        return boardManager.read(id);
    }

    public void delete(Long id){
        boardManager.delete(id);
    }


}
