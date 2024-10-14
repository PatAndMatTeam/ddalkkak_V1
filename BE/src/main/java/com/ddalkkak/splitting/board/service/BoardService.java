package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.domain.UploadFile;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Long create(final BoardCreateRequest createRequest,
                       final List<MultipartFile> multipartFiles,
                         final List<FileCreateRequest> fileInfoRequest) {
        Board board = Board.from(createRequest);
        BoardEntity entity = BoardEntity.fromModel(board);

        List<UploadFile> files = null;

        if (multipartFiles!=null && multipartFiles.size()>0){
            files = IntStream.range(0, multipartFiles.size())
                    .mapToObj(cnt -> UploadFile.from(multipartFiles.get(cnt),
                            fileInfoRequest.get(cnt)))
                    .collect(Collectors.toList());

            files.stream().forEach(file -> {
                entity.addFile(UploadFileEntity.fromModel(file));
            });
        }

        return boardRepository.save(entity).getId();
    }

    public Board read(Long id){
        return boardRepository.findById(id).get().toModel();
    }

    public List<Board> readAll(int start, int end){
        Pageable pageable = PageRequest.of(start, end);
        return boardRepository.findAll(pageable).getContent()
                .stream()
                .map(BoardEntity::toModel)
                .collect(Collectors.toList());
    }

    public List<Board> readAll(String category, int start, int end){
        Pageable pageable = PageRequest.of(start, end);
        return boardRepository.findByCategory(category, pageable).getContent()
                .stream()
                .map(BoardEntity::toModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, BoardUpdateRequest updateRequest, Optional<List<MultipartFile>> filesRequest, List<FileCreateRequest> fileInfoRequest){
        BoardEntity board = boardRepository.findById(id).get();
        board.changeTitle(updateRequest.title());
        board.changeContent(updateRequest.content());
        List<MultipartFile> files = filesRequest.orElse(Collections.emptyList());

        List<UploadFile> uploadFiles = IntStream.range(0, files.size())
                .mapToObj(cnt -> UploadFile.from(files.get(cnt),
                        fileInfoRequest.get(cnt)))
                .collect(Collectors.toList());

        // 1. 기존 파일 중 새로운 리스트에 없는 파일들은 삭제
        board.getFiles().stream()
                .forEach(board::removeFile); // DB에서 파일 삭제 처리

        // 2. 새로운 파일 중 기존 리스트에 없는 파일들은 추가
        uploadFiles.stream()
                .forEach(file -> {
                    board.addFile(UploadFileEntity.fromModel(file));
                });

        boardRepository.save(board);
    }


    @Transactional
    public Board update(Long id, BoardRecommendUpdateRequest boardRecommendUpdateRequest){
        BoardEntity board = boardRepository.findById(id).get();

        board.changeLeftCnt(boardRecommendUpdateRequest.leftRecommend());
        board.changeRightCnt(boardRecommendUpdateRequest.rightRecommend());
        return boardRepository.save(board).toModel();
    }

    public void delete(Long id){
        boardRepository.deleteById(id);
    }


    @Transactional
    public void visit(Long id){
        BoardEntity board = boardRepository.findById(id).get();
        Long visited = board.getVisited()+1;
        board.changeVisited(visited);
    }


}
