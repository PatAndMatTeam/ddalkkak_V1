package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.config.TestSecurityConfig;
import com.ddalkkak.splitting.user.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.List;
import java.util.Optional;

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
public class BoardApiV2Tests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardService boardService;

    @Autowired
    private JwtService jwtService;

    public String createTestToken(){
        return jwtService.createAccessToken("test", "test");
    }


    @DisplayName("카테고리 글을 생성할 수 있다.")
    @Test
    void createCategoryBoard() throws Exception {
        //given
        String category = Category.리그오브레전드.name;

        BoardCreateRequest request = BoardCreateRequest.builder()
                .title("카테고리 글 생성")
                .content("카테고리 글 생성")
                .writer("윤주영")
                .category(category)
                .build();

        MockMultipartFile create = new MockMultipartFile(
                "board", "board", "application/json",
                objectMapper.writeValueAsString(request).getBytes()
        );

        //when & then
        mockMvc.perform(multipart("/api/board/v2/"+category)
                .file(create)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                .with(csrf()))
                .andExpect(status().isCreated());
    }

    @DisplayName("카테고리 글을 조회할 수 있다.")
    @Test
    void getCategoryBoardOne() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest request = BoardCreateRequest.builder()
                .title("카테고리 글 생성")
                .content("카테고리 글 생성")
                .writer("윤주영")
                .category(category)
                .build();

        Long createId = boardService.create(request, Optional.empty(),Optional.empty());

        //when & then
        mockMvc.perform(get("/api/board/v2/"+category+"/"+createId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.content").value(request.getContent()))
                .andExpect(jsonPath("$.writer").value(request.getWriter()))
                .andExpect(jsonPath("$.category").value(Category.fromValue(category).toString()));
    }

    @DisplayName("카테고리 글 리스트를 조회할 수 있다.")
    @Test
    void getCategoryBoardAll() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest request1 = BoardCreateRequest.builder()
                .title("아리 꿀팁 좀")
                .content("아리 카운터 상대법좀요")
                .writer("윤주영")
                .category(category)
                .build();

        BoardCreateRequest request2 = BoardCreateRequest.builder()
                .title("다이어 가는법")
                .content("별거 없음")
                .writer("윤주영")
                .category(category)
                .build();

        Long createId1 = boardService.create(request1, Optional.empty(),Optional.empty());
        Long createId2 = boardService.create(request2, Optional.empty(),Optional.empty());

        //when & then
        mockMvc.perform(get("/api/board/v2/"+category+"/all")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.infos").isArray())  // 배열인지 확인
                .andExpect(jsonPath("$.infos.length()").value(2))  // 배열의 크기 확인
                .andExpect(jsonPath("$.infos[0].title").value(request1.getTitle()))
                .andExpect(jsonPath("$.infos[0].writer").value(request1.getWriter()))
                .andExpect(jsonPath("$.infos[0].category").value(Category.fromValue(category).toString()))
                .andExpect(jsonPath("$.infos[1].title").value(request2.getTitle()))
                .andExpect(jsonPath("$.infos[1].writer").value(request2.getWriter()))
                .andExpect(jsonPath("$.infos[1].category").value(Category.fromValue(category).toString()));
    }

    @DisplayName("카테고리 글을 삭제할 수 있다.")
    @Test
    void deleteCategoryBoard() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest request = BoardCreateRequest.builder()
                .title("아리 꿀팁 좀")
                .content("아리 카운터 상대법좀요")
                .writer("윤주영")
                .category(category)
                .build();

        Long createId = boardService.create(request, Optional.empty(),Optional.empty());


        //when & then
        mockMvc.perform(delete("/api/board/v2/{category}/{boardId}", category, createId)
                .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                        .with(csrf()))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @DisplayName("카테고리 글을 수정할 수 있다.")
    @Test
    void updateCategoryBoard() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest createRequest = BoardCreateRequest.builder()
                .title("아리 꿀팁 좀")
                .content("아리 카운터 상대법좀요")
                .writer("윤주영")
                .category(category)
                .build();

        Long createId = boardService.create(createRequest, Optional.empty(),Optional.empty());


        BoardUpdateRequest updateRequest = BoardUpdateRequest.builder()
                .title("제목 변경")
                .content("내용 변경")
                .build();

        MockMultipartFile update = new MockMultipartFile(
                "board", "board", "application/json",
                objectMapper.writeValueAsString(updateRequest).getBytes());

        //when $ then
        mockMvc.perform(multipart(HttpMethod.PATCH, "/api/board/v2/{category}/{id}", category, createId)
                        .file(update)
                .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isAccepted());

        mockMvc.perform(get( "/api/board/v2/{category}/{id}", category, createId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.title").value(updateRequest.title()))
                .andExpect(jsonPath("$.content").value(updateRequest.content()));
    }

    @DisplayName("분석글을 생성할 수 있다.")
    @Test
    void createAnalsisBoard() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest createParent = BoardCreateRequest.builder()
                .title("아리vs탈론")
                .content("누가 이기는가?")
                .writer("윤주영")
                .category(category)
                .build();

        Long parentId = boardService.create(createParent, Optional.empty(),Optional.empty());

        BoardCreateRequest createAnalysis = BoardCreateRequest.builder()
                .title("내가 볼땐 아리가 이김 아리 분석글.")
                .content("탈론은 1렙때 패줘야함")
                .writer("윤주영")
                .category(category)
                .build();

        MockMultipartFile create = new MockMultipartFile(
                "board", "board", "application/json",
                objectMapper.writeValueAsString(createAnalysis).getBytes()
        );

        //jwt
        String testToken = jwtService.createAccessToken("test", "test");

        //when & then
        mockMvc.perform(multipart("/api/board/v2/{category}/{parentId}", category, parentId)
                        .file(create)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "Bearer " + testToken) // JWT 토큰 추가
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @DisplayName("분석글을 조회할 수 있다.")
    @Test
    void getAnalsisBoard() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest createParent = BoardCreateRequest.builder()
                .title("아리vs탈론")
                .content("누가 이기는가?")
                .writer("윤주영")
                .category(category)
                .build();

        Long parentId = boardService.create(createParent, Optional.empty(),Optional.empty());

        BoardCreateRequest createAnalysis = BoardCreateRequest.builder()
                .title("내가 볼땐 아리가 이김 아리 분석글.")
                .content("탈론은 1렙때 패줘야함")
                .writer("윤주영")
                .category(category)
                .build();

        Long analysisId = boardService.create(parentId, createAnalysis, Optional.empty());

        //when & then
        mockMvc.perform(get("/api/board/v2/{category}/{parentId}/{analysisId}", category, parentId, analysisId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(analysisId))
                .andExpect(jsonPath("$.title").value(createAnalysis.getTitle()))
                .andExpect(jsonPath("$.content").value(createAnalysis.getContent()))
                .andExpect(jsonPath("$.writer").value(createAnalysis.getWriter()))
                .andExpect(jsonPath("$.category").value(Category.fromValue(category).toString()));
    }

    @DisplayName("분석글 리스트를 조회할 수 있다.")
    @Test
    void getAnalsisBoardAll() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest createParent = BoardCreateRequest.builder()
                .title("아리vs탈론")
                .content("누가 이기는가?")
                .writer("윤주영")
                .category(category)
                .build();

        Long parentId = boardService.create(createParent, Optional.empty(),Optional.empty());

        BoardCreateRequest createAnalysis1 = BoardCreateRequest.builder()
                .title("내가 볼땐 아리가 이김 아리 분석글.")
                .content("탈론은 1렙때 패줘야함")
                .writer("윤주영")
                .category(category)
                .build();

        Long analysisId1 = boardService.create(parentId, createAnalysis1, Optional.empty());

        BoardCreateRequest createAnalysis2 = BoardCreateRequest.builder()
                .title("내가 볼땐 탈론이 이김 탈론 분석글.")
                .content("아리는 1렙때 조심해야한다.")
                .writer("임정환")
                .category(category)
                .build();

        Long analysisId2 = boardService.create(parentId, createAnalysis2, Optional.empty());

        //when & then
        mockMvc.perform(get("/api/board/v2/{category}/{parentId}/all", category, parentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.infos.title").value(createParent.getTitle()))
                .andExpect(jsonPath("$.infos.content").value(createParent.getContent()))
                .andExpect(jsonPath("$.infos.writer").value(createParent.getWriter()))
                .andExpect(jsonPath("$.infos.childs").isArray())  // 배열인지 확인
                .andExpect(jsonPath("$.infos.childs.length()").value(2))  // 배열의 크기 확인
                .andExpect(jsonPath("$.infos.childs[0].id").value(analysisId1))
                .andExpect(jsonPath("$.infos.childs[0].title").value(createAnalysis1.getTitle()))
                .andExpect(jsonPath("$.infos.childs[0].writer").value(createAnalysis1.getWriter()))
                .andExpect(jsonPath("$.infos.childs[0].category").value(Category.fromValue(category).toString()))
                .andExpect(jsonPath("$.infos.childs[1].id").value(analysisId2))
                .andExpect(jsonPath("$.infos.childs[1].title").value(createAnalysis2.getTitle()))
                .andExpect(jsonPath("$.infos.childs[1].writer").value(createAnalysis2.getWriter()))
                .andExpect(jsonPath("$.infos.childs[1].category").value(Category.fromValue(category).toString()));
    }

    @DisplayName("분석글을 삭제할 수 있다.")
    @Test
    void deleteAnalsisBoardAll() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest createParent = BoardCreateRequest.builder()
                .title("아리vs탈론")
                .content("누가 이기는가?")
                .writer("윤주영")
                .category(category)
                .build();

        Long parentId = boardService.create(createParent, Optional.empty(),Optional.empty());

        BoardCreateRequest createAnalysis1 = BoardCreateRequest.builder()
                .title("내가 볼땐 아리가 이김 아리 분석글.")
                .content("탈론은 1렙때 패줘야함")
                .writer("윤주영")
                .category(category)
                .build();

        Long analysisId = boardService.create(parentId, createAnalysis1, Optional.empty());


        //when & then
        mockMvc.perform(delete("/api/board/v2/{category}/{parentId}/{analysisId}", category, parentId, analysisId)
                .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                .with(csrf()))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @DisplayName("분석글을 수정할 수 있다.")
    @Test
    void updateAnalsisBoardAll() throws Exception {
        //given
        String category = Category.리그오브레전드.name;
        BoardCreateRequest createParent = BoardCreateRequest.builder()
                .title("아리vs탈론")
                .content("누가 이기는가?")
                .writer("윤주영")
                .category(category)
                .build();

        Long parentId = boardService.create(createParent, Optional.empty(),Optional.empty());

        BoardCreateRequest createAnalysis = BoardCreateRequest.builder()
                .title("내가 볼땐 아리가 이김 아리 분석글.")
                .content("탈론은 1렙때 패줘야함")
                .writer("윤주영")
                .category(category)
                .build();

        Long analysisId = boardService.create(parentId, createAnalysis, Optional.empty());

        BoardUpdateRequest updateRequest = BoardUpdateRequest.builder()
                .title("아리가 이기기는 힘들거 같아")
                .content("6렙 이후에 탈론이 엄청 쎄져서 이기기 힘듬")
                .build();

        MockMultipartFile update = new MockMultipartFile(
                "board", "board", "application/json",
                objectMapper.writeValueAsString(updateRequest).getBytes());

        //when $ then
        mockMvc.perform(multipart(HttpMethod.PATCH, "/api/board/v2/{category}/parentId}/{analysisId}", category, parentId, analysisId)
                        .file(update)
                         .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                        .contentType(MediaType.MULTIPART_FORM_DATA))

                .andExpect(status().isAccepted());
    }

}
