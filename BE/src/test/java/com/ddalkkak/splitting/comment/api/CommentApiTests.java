package com.ddalkkak.splitting.comment.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.FileInfoCreateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentDeleteRequest;
import com.ddalkkak.splitting.comment.service.CommentService;
import com.ddalkkak.splitting.config.TestSecurityConfig;
import com.ddalkkak.splitting.user.service.JwtService;
import com.ddalkkak.splitting.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class) // 테스트용 보안 구성 클래스 추가
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;

    @Autowired
    private JwtService jwtService;

    public String createTestToken(){
        return jwtService.createAccessToken("test", "test");
    }

    @DisplayName("댓글을 작성할 수 있다.")
    @Test
    void registCommentApi() throws Exception {
        //given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .category(Category.정치.name)
                .title("대한민국 인구 미래")
                .content("암담하다")
                .writer("윤주영")
                .build();

        List<MultipartFile> multipartFiles1 = List.of();
        List<FileInfoCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(boardCreateRequest, Optional.of(multipartFiles1), Optional.of(fileInfos1));


        CommentCreateRequest createRequest = CommentCreateRequest.builder()
                .writer("yjy")
                .password("1234")
                .content("ㅋㅋㅋㅋ")
                .parentId(0L)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/board/{boardId}/comment", boardId)
                    .content(objectMapper.writeValueAsString(createRequest))
                    .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

    }

    @DisplayName("댓글을 삭제할 수 있다.")
    @Test
    void removeCommentApi() throws Exception {
        //given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .category(Category.정치.name)
                .title("대한민국 인구 미래")
                .content("암담하다")
                .writer("윤주영")
                .build();
        List<MultipartFile> multipartFiles1 = List.of();
        List<FileInfoCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(boardCreateRequest, Optional.of(multipartFiles1), Optional.of(fileInfos1));

        CommentCreateRequest createRequest = CommentCreateRequest.builder()
                .writer("윤주영")
                .password("1234")
                .content("윤주영")
                .parentId(0L)
                .build();

        Long commentId = commentService.create(boardId, createRequest);

        CommentDeleteRequest deleteRequest = CommentDeleteRequest.builder()
                .password(createRequest.password())
                .build();

        //when-then
        mockMvc.perform(delete("/api/board/v2/{boardId}/comment/{commendId}",boardId, commentId)
                        .content(objectMapper.writeValueAsString(deleteRequest))
                        .header("Authorization", "Bearer " + createTestToken()) // JWT 토큰 추가
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isAccepted());
    }
}
