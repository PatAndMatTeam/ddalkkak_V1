package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.infrastructure.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;


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
        boardService.create(givenRequest);

        //then
        //...
        BoardDto result = boardService.read(1L);

        assertEquals(givenRequest.title(), result.title());
        assertEquals(givenRequest.content(), result.content());
        assertEquals(givenRequest.category(), result.category());
        assertEquals(givenRequest.writer(), result.writer());

    }

}
