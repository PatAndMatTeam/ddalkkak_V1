package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.dto.BoardCreateDto;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class BoardManagerTests {

    @Autowired
    private BoardManager boardManager;


    @DisplayName("글을 생성할 수 있다.")
    @Test
    void createBoard(){
        //given
        //파일
        UploadFileCreateDto file = UploadFileCreateDto.builder()
                .fileName("test")
                .build();
        //글
        BoardCreateDto boardCreatDto = BoardCreateDto.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .files(List.of(file))
                .build();

        //when
        Long createdId = boardManager.create(boardCreatDto);

        //then
        //...
        BoardDto result = boardManager.read(createdId);

        assertEquals(createdId, result.id());
        assertEquals(boardCreatDto.getTitle(), result.title());
        assertEquals(boardCreatDto.getContent(), result.content());
        assertEquals(boardCreatDto.getCategory(), result.category());
        assertEquals(boardCreatDto.getWriter(), result.writer());
    }

    @DisplayName("번호로 글을 찾을 수 있다.")
    @Test
    void findBoardSucceed(){
        //given
        //파일
        UploadFileCreateDto file1 = UploadFileCreateDto.builder()
                .fileName("test1")
                .build();
        UploadFileCreateDto file2 = UploadFileCreateDto.builder()
                .fileName("test2")
                .build();
        //글
        BoardCreateDto boardCreatDto = BoardCreateDto.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .files(List.of(file1,file2))
                .build();

        Long createdId = boardManager.create(boardCreatDto);


        //when
        BoardDto result = boardManager.read(createdId);

        //then
        assertEquals(createdId, result.id());
        assertEquals(boardCreatDto.getTitle(), result.title());
        assertEquals(boardCreatDto.getContent(), result.content());
        assertEquals(boardCreatDto.getCategory(), result.category());
        assertEquals(boardCreatDto.getWriter(), result.writer());
        assertEquals(boardCreatDto.getFiles().size(), 2);
    }

    @DisplayName("없는 번호로 글을 찾을 수 없다.")
    @Test
    void findBoardFailed(){
        //given
        Long id = 0L;

        //when
        //then
        assertThrows(BoardException.BoardNotFoundException.class, () -> {
            boardManager.read(id);
        });
    }

    @DisplayName("글 리스트를 조회할 수 있다.")
    @Test
    void findBoards(){
        //파일
        UploadFileCreateDto file = UploadFileCreateDto.builder()
                .fileName("test")
                .build();

        BoardCreateDto boardCreatDto1 = BoardCreateDto.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .files(List.of(file))
                .build();

        boardManager.create(boardCreatDto1);

        BoardCreateDto boardCreatDto2 = BoardCreateDto.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .files(List.of(file))
                .build();
        boardManager.create(boardCreatDto2);


        //given
        int start = 0;
        int end = 10;

        //when
        List<BoardDto> result = boardManager.readAll(start,end);

        //then
        assertEquals(boardCreatDto1.getTitle(), result.get(0).title());
        assertEquals(boardCreatDto1.getContent(), result.get(0).content());
        assertEquals(boardCreatDto1.getCategory(), result.get(0).category());
        assertEquals(boardCreatDto1.getWriter(), result.get(0).writer());

        assertEquals(boardCreatDto2.getTitle(), result.get(1).title());
        assertEquals(boardCreatDto2.getContent(), result.get(1).content());
        assertEquals(boardCreatDto2.getCategory(), result.get(1).category());
        assertEquals(boardCreatDto2.getWriter(), result.get(1).writer());
    }

    @DisplayName("글을 삭제할 수 있다.")
    @Test
    void deleteBoard(){
        //given
        BoardCreateDto boardCreatDto = BoardCreateDto.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .build();

        Long createdId = boardManager.create(boardCreatDto);

        //when
        boardManager.delete(createdId);

        //then
        assertThrows(BoardException.BoardNotFoundException.class, () -> {
            boardManager.read(createdId);
        });
    }

    @DisplayName("글을 변경할 수 있다.")
    @Test
    void updateBoard(){
        //given
        BoardCreateDto boardCreatDto = BoardCreateDto.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .build();

        Long createdId = boardManager.create(boardCreatDto);

        BoardUpdateRequest boardUpdateRequest = BoardUpdateRequest.builder()
                .title("제목을 변경했어요")
                .content("내용도 변경했어요")
                .build();
        //when
        boardManager.update(createdId, boardUpdateRequest);

        //then
        BoardDto actual = boardManager.read(createdId);

        assertEquals(boardUpdateRequest.title(), actual.title());
        assertEquals(boardUpdateRequest.content(), actual.content());

    }

}
