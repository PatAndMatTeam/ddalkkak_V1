package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Import(TestSecurityConfig.class) // 테스트용 보안 구성 클래스 추가
@WebMvcTest(controllers = {BoardApiV1.class})
@ActiveProfiles("test")
public class BoardApiV1ValidTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;


    @DisplayName("글 생성시 카테고리, 제목, 내용, 작성자는 빈칸, null, 공백을 제외하고 작성되야 합니다.")
    @Test
    void isValidWhenCreateBoard() throws Exception {
        //given
        BoardCreateRequest createRequest = BoardCreateRequest.builder()
                .category("lol")
                .title("")
                .content("test")
                .writer("test")
                .build();

        MockMultipartFile boardPart = new MockMultipartFile(
                "board", "board", "application/json", objectMapper.writeValueAsString(createRequest).getBytes());

        ResultActions resultActions = mockMvc.perform(multipart("/api/board/")
                        .file(boardPart)
                .with(csrf())
                .contentType(MediaType.MULTIPART_FORM_DATA));

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.message").exists())  // 응답에 errors 필드가 있어야 함
                .andExpect(jsonPath("$.message").value("title: 제목을 입력해주세요"));  // title 필드에서 에러 발생
    }

}
