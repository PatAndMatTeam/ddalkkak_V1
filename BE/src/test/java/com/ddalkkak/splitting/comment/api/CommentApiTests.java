package com.ddalkkak.splitting.comment.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentDeleteRequest;
import com.ddalkkak.splitting.comment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @DisplayName("댓글을 작성할 수 있다.")
    @Test
    void registCommentApi() throws Exception {
        //given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .category("정치")
                .title("대한민국 인구 미래")
                .content("암담하다")
                .writer("윤주영")
                .build();
        Long boardId = boardService.create(boardCreateRequest, null);

        CommentCreateRequest createRequest = CommentCreateRequest.builder()
                .writer("yjy")
                .password("1234")
                .content("ㅋㅋㅋㅋ")
                .parentId(0L)
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/board/{boardId}/comment", boardId)
                    .content(objectMapper.writeValueAsString(createRequest))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

    }

    @DisplayName("댓글을 삭제할 수 있다.")
    @Test
    void removeCommentApi() throws Exception {
        //given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .category("정치")
                .title("대한민국 인구 미래")
                .content("암담하다")
                .writer("윤주영")
                .build();
       Long boardId = boardService.create(boardCreateRequest, null);

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
        mockMvc.perform(delete("/api/board/{boardId}/comment/{commendId}",boardId, commentId)
                        .content(objectMapper.writeValueAsString(deleteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isAccepted());
    }
}
