package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BoardApi.class})
@ActiveProfiles("test")
public class BoardApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @DisplayName("글을 작성할 수 있다.")
    @Test
    public void testCreateBoard() throws Exception {
        //given

        //파일
        MockMultipartFile mockFile = new MockMultipartFile(
                "files", // 여기서 필드 이름은 실제 매핑되는 필드와 동일하게 설정
                "test.jpg",
                "image/jpeg",
                "Test data".getBytes());
        int width = 200;
        int height = 200;

        //글
        String category = "정치";
        String title = "우리 성공할 수 있을까?";
        String content = "무조건 성공 해야지 어?";
        String writer = "윤주영";

        // MockMvc를 사용한 multipart 요청
        mockMvc.perform(multipart("/api/board/")
                        .file(mockFile) // 파일 전달
                        .param("category", category) // 다른 파라미터 전달
                        .param("title", title)
                        .param("content", content)
                        .param("writer", writer)
                        .param("width", String.valueOf(width))
                        .param("height", String.valueOf(height))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andDo(print());

        // BoardService.create() 메서드 호출 확인
        verify(boardService).create(any(BoardCreateRequest.class));
        }

}


