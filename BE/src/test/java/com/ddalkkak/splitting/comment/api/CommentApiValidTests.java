package com.ddalkkak.splitting.comment.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.service.CommentService;
import com.ddalkkak.splitting.config.TestSecurityConfig;
import com.ddalkkak.splitting.user.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class) // 테스트용 보안 구성 클래스 추가
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommentApiValidTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtService jwtService; // 실제 jwtService 빈을 가져오기

    private String jwtToken;

    @BeforeEach
    void setUp() {
        // jwtService를 사용하여 JWT 토큰 생성
        jwtToken = jwtService.createAccessToken("test", "test"); // "testUser"는 필요한 사용자 정보로 변경
    }


    @DisplayName("댓글 생성시 작성자, 비밀번호, 내용은 기입되어야 합니다.")
    @Test
    void registCommentApi() throws Exception {
        //given
        CommentCreateRequest createRequest = CommentCreateRequest.builder()
                .writer("yjy")
                .password("")
                .content("ㅋㅋㅋㅋ")
                .parentId(0L)
                .build();

        ResultActions resultActions = mockMvc.perform(multipart("/api/board/1/comment")
                .content(objectMapper.writeValueAsString(createRequest))
                        .with(csrf())
                .header("Authorization", "Bearer " + jwtToken) // JWT 토큰
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.message").exists())  // 응답에 errors 필드가 있어야 함
                .andExpect(jsonPath("$.message").value("password: 비밀번호를 입력해주세요"));  // title 필드에서 에러 발생
    }

}
