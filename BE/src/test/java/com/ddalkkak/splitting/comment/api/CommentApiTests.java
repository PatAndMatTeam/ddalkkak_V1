package com.ddalkkak.splitting.comment.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CommentApi.class})
@ActiveProfiles("test")
public class CommentApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @DisplayName("댓글을 작성할 수 있다.")
    @Test
    void registCommentApi() throws Exception {
        //given
        CommentCreateRequest createRequest = CommentCreateRequest.builder()
                .writer("yjy")
                .password("1234")
                .content("ㅋㅋㅋㅋ")
                .parentId(0L)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(multipart("/api/board/1/comment")
                .content(objectMapper.writeValueAsString(createRequest))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated()); // 400 Bad Request
    }

    @DisplayName("댓글을 삭제할 수 있다.")
    @Test
    void removeCommentApi() throws Exception {
        //given
        BoardCreateRequest createRequest = BoardCreateRequest.builder()
                .category("롤")
                .title("")
                .content("test")
                .writer("test")
                .width(1)
                .height(1)
                .build();

        ResultActions resultActions = mockMvc.perform(multipart("/api/board/1/comment")
                .param("title", createRequest.title())
                .param("content", createRequest.content())
                .param("category", createRequest.category())
                .param("writer", createRequest.writer())
                .param("width", String.valueOf(createRequest.width()))
                .param("height", String.valueOf(createRequest.height()))
                .characterEncoding("utf-8")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));

        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest())  // 400 Bad Request
                .andExpect(jsonPath("$.message").exists())  // 응답에 errors 필드가 있어야 함
                .andExpect(jsonPath("$.message").value("title: 제목을 입력해주세요"));
    }
}
