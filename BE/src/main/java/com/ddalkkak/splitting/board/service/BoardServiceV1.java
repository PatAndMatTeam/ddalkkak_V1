package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.domain.UploadFile;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceV1 {

    private final BoardManagerV1 boardManager;
    private final FileService fileService;
    private final BoardRepository boardRepository;

    public Long create(final BoardCreateRequest createRequest,
                       final List<MultipartFile> multipartFiles,
                         final List<FileCreateRequest> fileInfoRequest) {
        List<UploadFile> files = null;

        if (multipartFiles!=null){
            files = IntStream.range(0, multipartFiles.size())
                    .mapToObj(cnt -> UploadFile.from(multipartFiles.get(cnt),
                            fileInfoRequest.get(cnt)))
                    .collect(Collectors.toList());
        }
        Board board = Board.from(createRequest, files);

        return boardManager.create(board);
    }


    public Board read(Long id){
        Board board = boardManager.read(id);
        //lazi loading
        log.info("{}:::", board);
        return board;
    }

    public List<Board> readAll(int start, int end){
        return boardManager.readAll(start, end);
    }

    public void update(Long id, BoardUpdateRequest updateRequest, List<MultipartFile> files, List<FileCreateRequest> fileInfoRequest){
        BoardEntity board = boardRepository.findById(id).get();



        List<UploadFile> uploadFiles = IntStream.range(0, files.size())
                .mapToObj(cnt -> UploadFile.from(files.get(cnt),
                        fileInfoRequest.get(cnt)))
                .collect(Collectors.toList());

        //Board update = board.update(updateRequest, uploadFiles);

        // 1. 기존 파일 중 새로운 리스트에 없는 파일들은 삭제
        board.getFiles().stream()
                .forEach(board::removeFile); // DB에서 파일 삭제 처리

        // 2. 새로운 파일 중 기존 리스트에 없는 파일들은 추가
        uploadFiles.stream()
                .forEach(board::addFile); // DB에 파일 추가


//       boardManager.update(update);
    }


    public Board update(Long id, BoardRecommendUpdateRequest boardRecommendUpdateRequest){
        boardManager.update(id, boardRecommendUpdateRequest);

        return boardManager.read(id);
    }

    public void delete(Long id){
        boardManager.delete(id);
    }


}
