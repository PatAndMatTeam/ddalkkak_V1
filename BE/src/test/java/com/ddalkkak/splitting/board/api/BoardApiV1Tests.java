package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.FileInfoCreateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class) // 테스트용 보안 구성 클래스 추가
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BoardApiV1Tests {
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
                .category("politics")
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
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andDo(print());


        }

    @DisplayName("글 리스트를 조회할 수 있다.")
    @Test
    public void testSelectBaordList() throws Exception {
        //given
        BoardCreateRequest createRequest1 = BoardCreateRequest.builder()
                .category("baseball")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();
        List<MultipartFile> multipartFiles1 = List.of();
        List<FileInfoCreateRequest> fileInfos1 = List.of();

        boardService.create(createRequest1, multipartFiles1, fileInfos1);

        BoardCreateRequest createRequest2 = BoardCreateRequest.builder()
                .category("baseball")
                .title("정치입니다.")
                .content("정치 내용입니다.")
                .writer("임정환")
                .build();
        List<MultipartFile> multipartFiles2 = List.of();
        List<FileInfoCreateRequest> fileInfos2 = List.of();

        boardService.create(createRequest2, multipartFiles2, fileInfos2);

        //when-then
       ResultActions resultActions =  mockMvc.perform(get("/api/board/all")
               .with(csrf()));

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
                .category("baseball")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();
        List<MultipartFile> multipartFiles1 = List.of();
        List<FileInfoCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(createRequest1, multipartFiles1, fileInfos1);

        //when-then
        mockMvc.perform(get("/api/board/{boardId}", boardId)
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(boardId))
                .andExpect(jsonPath("$.category").value(Category.fromValue(createRequest1.getCategory()).toString()))
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
                .category("baseball")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();
        List<MultipartFile> multipartFiles1 = List.of();
        List<FileInfoCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(createRequest1, multipartFiles1, fileInfos1);

        //when-then
        mockMvc.perform(delete("/api/board/{boardId}", boardId)
                        .with(csrf()))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @DisplayName("글을 변경할 수 있다.")
    @Test
    public void testUpdateBoard() throws Exception {
        //given
        BoardCreateRequest create = BoardCreateRequest.builder()
                .category("baseball")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();

        List<MultipartFile> multipartFiles1 = List.of();
        List<FileInfoCreateRequest> fileInfos1 = List.of(FileInfoCreateRequest.builder()
                        .fileTitle("test")
                        .width(100)
                        .height(100)
                .build());

        Long boardId = boardService.create(create, multipartFiles1, fileInfos1);

        BoardCreateRequest update = BoardCreateRequest.builder()
                .category("baseball")
                .title("제목 변경")
                .content("내용 변경")
                .writer("윤주영")
                .build();
        //when-then
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/api/board/{id}", boardId)
                        .file(new MockMultipartFile("board", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(update).getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf()))

                .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/board/{boardId}", boardId)
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.title").value(update.getTitle()))
                .andExpect(jsonPath("$.content").value(update.getContent()))
                .andDo(print());
    }

    @DisplayName("글을 추천할 수 있다.")
    @Test
    public void testRecommendBoard() throws Exception {
        //given
        BoardCreateRequest createRequest1 = BoardCreateRequest.builder()
                .category("baseball")
                .title("제목입니다.")
                .content("내용입니다.")
                .writer("윤주영")
                .build();

        List<MultipartFile> multipartFiles1 = List.of();
        List<FileInfoCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(createRequest1, multipartFiles1, fileInfos1);

        BoardRecommendUpdateRequest request = BoardRecommendUpdateRequest.builder()
                .leftRecommend(1)
                .rightRecommend(2)
                .build();

        //when-then
        mockMvc.perform(patch("/api/board/{boardId}/recommend", boardId)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.leftRecommend").value(request.leftRecommend()))
                .andExpect(jsonPath("$.rightRecommend").value(request.rightRecommend()))
                .andDo(print());

    }

}


