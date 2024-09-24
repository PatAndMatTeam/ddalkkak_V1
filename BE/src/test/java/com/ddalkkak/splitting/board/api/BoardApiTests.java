package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BoardApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardService boardService;

    @DisplayName("글을 작성할 수 있다.")
    @Test
    public void testCreateBoard() throws Exception {
        //given
        //파일
        //글
        BoardCreateRequest createRequest = BoardCreateRequest.builder()
                .category("정치")
                .title("우리 성공할 수 있을까?")
                .content("무조건 성공 해야지 어?")
                .writer("윤주영")
                .build();

        MockMultipartFile borad = new MockMultipartFile(
                "board", "board", "application/json",
                objectMapper.writeValueAsString(createRequest).getBytes());

        //when-then
        mockMvc.perform(multipart("/api/board/")
                        .file(borad) // 파일 전달
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andDo(print());


        }

    @DisplayName("글 리스트를 조회할 수 있다.")
    @Test
    public void testSelectBaordList() throws Exception {
        //given
        BoardCreateRequest createRequest1 = BoardCreateRequest.builder()
                .category("롤")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();

        boardService.create(createRequest1, null);

        BoardCreateRequest createRequest2 = BoardCreateRequest.builder()
                .category("정치")
                .title("정치입니다.")
                .content("정치 내용입니다.")
                .writer("임정환")
                .build();

       boardService.create(createRequest2, null);

        //when-then
       ResultActions resultActions =  mockMvc.perform(get("/api/board/all"));

        resultActions
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.infos").exists())
                .andExpect(jsonPath("$.infos[0].title").value(createRequest1.getTitle()))
                .andExpect(jsonPath("$.infos[0].writer").value(createRequest1.getWriter()))
                .andExpect(jsonPath("$.infos[1].title").value(createRequest2.getTitle()))
                .andExpect(jsonPath("$.infos[1].writer").value(createRequest2.getWriter()));

    }

    @DisplayName("특정 글을 조회할 수 있다.")
    @Test
    public void testSelectDetailedBoard() throws Exception {
        //given
        BoardCreateRequest createRequest1 = BoardCreateRequest.builder()
                .category("롤")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();

        Long boardId = boardService.create(createRequest1, null);

        //when-then
        mockMvc.perform(get("/api/board/{boardId}", boardId))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(boardId))
                .andExpect(jsonPath("$.category").value(createRequest1.getCategory()))
                .andExpect(jsonPath("$.title").value(createRequest1.getTitle()))
                .andExpect(jsonPath("$.content").value(createRequest1.getContent()))
                .andExpect(jsonPath("$.writer").value(createRequest1.getWriter()))
                .andDo(print());


    }

    @DisplayName("글을 삭제할 수 있다.")
    @Test
    public void testDeleteBoard() throws Exception {
        //given
        BoardCreateRequest createRequest1 = BoardCreateRequest.builder()
                .category("롤")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();

        Long boardId = boardService.create(createRequest1, null);

        //when-then
        mockMvc.perform(delete("/api/board/{boardId}", boardId))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @DisplayName("글을 변경할 수 있다.")
    @Test
    public void testUpdateBoard() throws Exception {
        //given
        BoardCreateRequest createRequest1 = BoardCreateRequest.builder()
                .category("롤")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();

        Long boardId = boardService.create(createRequest1, null);

        //when-then
        mockMvc.perform(patch("/api/board/{boardId}", boardId)
                        .content(objectMapper.writeValueAsString(createRequest1))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andDo(print());

        mockMvc.perform(get("/api/board/{boardId}", boardId))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.title").value(createRequest1.getTitle()))
                .andExpect(jsonPath("$.content").value(createRequest1.getContent()))
                .andDo(print());
    }

    @DisplayName("글을 추천할 수 있다.")
    @Test
    public void testRecommendBoard() throws Exception {
        //given
        BoardCreateRequest createRequest1 = BoardCreateRequest.builder()
                .category("롤")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();

        Long boardId = boardService.create(createRequest1, null);

        BoardRecommendUpdateRequest request = BoardRecommendUpdateRequest.builder()
                .leftRecommend(1)
                .rightRecommend(2)
                .build();

        //when-then
        mockMvc.perform(patch("/api/board/{boardId}/recommend", boardId)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.leftRecommend").value(request.leftRecommend()))
                .andExpect(jsonPath("$.rightRecommend").value(request.rightRecommend()))
                .andDo(print());

    }

}


