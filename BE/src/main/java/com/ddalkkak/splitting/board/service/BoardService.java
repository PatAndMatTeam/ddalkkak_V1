package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardVoteUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileInfoCreateRequest;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.domain.UploadFile;
import com.ddalkkak.splitting.board.exception.BoardErrorCode;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.board.exception.UploadFileErrorCode;
import com.ddalkkak.splitting.board.exception.UploadFileException;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
                       final Optional<List<MultipartFile>> multipartFiles,
                         final Optional<List<FileInfoCreateRequest>> fileInfoRequest) {

        List<MultipartFile> files = multipartFiles.orElse(Collections.emptyList());
        List<FileInfoCreateRequest> fileInfos = fileInfoRequest.orElse(Collections.emptyList());

        int filesCount = files.size();
        int fileInfoCount = fileInfos.size();
        if (filesCount!=fileInfoCount){
            throw new UploadFileException.CannotBeUploadedException(UploadFileErrorCode.NOT_MATCH_COUNT,
                    "file cnt: "+filesCount+"\n file info cnt: "+fileInfoCount);
        }

        Board board = Board.from(createRequest);
        BoardEntity create = BoardEntity.fromModel(board);

        Optional.ofNullable(files)
                .filter(f -> !f.isEmpty())
                .ifPresent(f -> {
                    IntStream.range(0, f.size())
                                    .mapToObj(cnt -> UploadFile.from(files.get(cnt),
                                            fileInfos.get(cnt)))
                            .map(UploadFileEntity::fromModel)
                            .forEach(create::addFile);
                });
        return boardRepository.save(create).getId();
    }

    public Long create(final Long parentId,
                        final BoardCreateRequest createRequest,
                       final Optional<List<MultipartFile>> multipartFiles) {

        BoardEntity parent = boardRepository.findById(parentId)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, parentId));

        List<MultipartFile> files = multipartFiles.orElse(Collections.emptyList());
        Board child = Board.from(createRequest);

        BoardEntity create = BoardEntity.fromModel(child);

        Optional.ofNullable(files)
                .filter(f -> !files.isEmpty())
                .ifPresent(f -> {
                    files.stream()
                            .map(UploadFile::from)
                            .map(UploadFileEntity::fromModel)
                            .forEach(create::addFile);
                });

        create.addParent(parent);
        //parent.addChild(create);

        return boardRepository.save(create).getId();
    }

    public Board read(Long id){
        return boardRepository
                .findById(id).orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id))
                .toModel();
    }

    public Board readV1(String category, Long id){
        return boardRepository.findByCategory(category, id).toModel();
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
        return boardRepository.findByCategoryAndParentIsNull(Category.fromValue(category), pageable)
                .stream()
                .map(BoardEntity::toModel)
                .collect(Collectors.toList());
    }

    public Board readAll(Long parentId, String category, int start, int end){
        Pageable pageable = PageRequest.of(start, end);

        BoardEntity parent = boardRepository.findById(parentId).get();
        List<BoardEntity> child = boardRepository.findByCategoryAndParentId(Category.fromValue(category),
                parentId,
                pageable);

        Board result = parent.toModel();
        child.forEach(c -> {
            result.addChild(c.toModel());
        });

        return result;
    }

    @Transactional
    public void update(Long id, BoardUpdateRequest updateRequest,
                       Optional<List<MultipartFile>> multipartFiles,
                       Optional<List<FileInfoCreateRequest>> fileInfoRequest){

        List<MultipartFile> files = multipartFiles.orElse(Collections.emptyList());
        List<FileInfoCreateRequest> fileInfos = fileInfoRequest.orElse(Collections.emptyList());

        BoardEntity read = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id));
        read.changeTitle(updateRequest.title());
        read.changeContent(updateRequest.content());


        List<UploadFile> uploadFiles = IntStream.range(0, files.size())
                .mapToObj(cnt -> UploadFile.from(files.get(cnt),
                        fileInfos.get(cnt)))
                .collect(Collectors.toList());

        // 1. 기존 파일 중 새로운 리스트에 없는 파일들은 삭제
        List<UploadFileEntity> filesToRemove = new ArrayList<>(read.getFiles());
        filesToRemove.forEach(read::removeFile);

        // 2. 새로운 파일 중 기존 리스트에 없는 파일들은 추가
        uploadFiles
                .forEach(file -> {
                    read.addFile(UploadFileEntity.fromModel(file));
                });

        //return boardRepository.save(read).getId();
    }


    @Transactional
    public Board update(Long id, BoardVoteUpdateRequest boardVoteUpdateRequest){
        BoardEntity read = boardRepository.findById(id)
                        .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id));

        read.changeLeftVote(boardVoteUpdateRequest.leftVote());
        read.changeRightVote(boardVoteUpdateRequest.rightVote());
        return boardRepository.save(read).toModel();
    }

    @Transactional
    public Long recommend(Long id){
        BoardEntity read = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id));

        read.increaseRecommend();
        return boardRepository.save(read).toModel().getRecommend();
    }

    public void delete(Long id){
         boardRepository.deleteById(id);
    }


    @Transactional
    public void visit(Long id){
        BoardEntity read = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException.BoardNotFoundException(BoardErrorCode.BOARD_NOT_FOUND, id));
        Long visited = read.getVisited()+1;
        read.changeVisited(visited);
    }

    public List<Board> search(String category, String title, String content, int start, int end) {
        Pageable pageable = PageRequest.of(start, end);

        return boardRepository.search(Category.fromValue(category), title, content ,pageable)
                .stream()
                .map(BoardEntity::toModel)
                .collect(Collectors.toList());
    }

    public List<Board> search(Long categoryBoardId, String category, String title, String content, int start, int end) {
        Pageable pageable = PageRequest.of(start, end);

        return boardRepository.search(categoryBoardId, Category.fromValue(category), title, content ,pageable)
                .stream()
                .map(BoardEntity::toModel)
                .collect(Collectors.toList());
    }
}
