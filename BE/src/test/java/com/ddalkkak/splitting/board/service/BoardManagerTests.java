package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardPageableRequest;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.exception.BoardException;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        BoardCreateRequest givenRequest = BoardCreateRequest.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .build();

        //when
        boardManager.create(givenRequest);

        //then
        //...
        BoardDto result = boardManager.read(1L);

        assertEquals(givenRequest.title(), result.title());
        assertEquals(givenRequest.content(), result.content());
        assertEquals(givenRequest.category(), result.category());
        assertEquals(givenRequest.writer(), result.writer());
    }

    @DisplayName("번호로 글을 찾을 수 있다.")
    @Test
    void findBoardSucceed(){
        BoardCreateRequest givenRequest = BoardCreateRequest.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .build();

        boardManager.create(givenRequest);

        //given
        Long id = 1L;

        //when
        BoardDto result = boardManager.read(id);

        //then
        assertEquals(id, result.id());
        assertEquals(givenRequest.title(), result.title());
        assertEquals(givenRequest.content(), result.content());
        assertEquals(givenRequest.category(), result.category());
        assertEquals(givenRequest.writer(), result.writer());
    }

    @DisplayName("없는 번호로 글을 찾을 수 없다.")
    @Test
    void findBoardFailed(){
        //given
        Long id = 1L;

        //when
        //then
        assertThrows(BoardException.BoardNotFoundException.class, () -> {
            boardManager.read(id);
        });
    }

    @DisplayName("글 리스트를 조회할 수 있다.")
    @Test
    void findBoards(){
        BoardCreateRequest givenRequest1 = BoardCreateRequest.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .build();

        boardManager.create(givenRequest1);

        BoardCreateRequest givenRequest2 = BoardCreateRequest.builder()
                .title("갈라치기 해보자")
                .content("어떻게 하면 잘 만들수 있을까?")
                .category(Category.정치.name())
                .writer("윤주영")
                .build();
        boardManager.create(givenRequest1);


        //given
        int start = 0;
        int end = 10;

        //when
        List<BoardDto> result = boardManager.readAll(start,end);

        //then
        result.forEach(x -> {
            System.out.println(x);
        });

    }

}
