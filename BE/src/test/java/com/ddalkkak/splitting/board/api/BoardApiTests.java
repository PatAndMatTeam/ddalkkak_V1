package com.ddalkkak.splitting.board.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@WebMvcTest(controllers = {BoardApi.class})
@ActiveProfiles("test")
public class BoardApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @Test
    public void testCreateBoard() throws Exception {
// Given
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/testimage.jpg")) {
            if (inputStream == null) {
                throw new IOException("File not found in classpath");
            }
            //file
            MockMultipartFile givenFile = new MockMultipartFile("file", "image1.jpg",
                    "image/jpeg", inputStream);

            List<MockMultipartFile> files = new ArrayList<>();
            files.add(givenFile);

            BoardCreateRequest boardCreateRequest = new BoardCreateRequest(
                    "정치",
                    "우리 성공할 수 있을까?",
                    "무조건 성공 해야지 어?",
                    "윤주영",
                    new BoardCreateRequest.FileUploadRequest(
                            Collections.singletonList(givenFile),
                            200,
                            200
                    )
            );

            // Convert BoardCreateRequest to JSON (excluding files)
            String boardCreateRequestJson = objectMapper.writeValueAsString(new BoardCreateRequest(
                    boardCreateRequest.category(),
                    boardCreateRequest.title(),
                    boardCreateRequest.content(),
                    boardCreateRequest.writer(),
                    new BoardCreateRequest.FileUploadRequest(
                            Collections.emptyList(), // No files in the JSON representation
                            boardCreateRequest.uploadFile().width(),
                            boardCreateRequest.uploadFile().height()
                    )
            ));

            // When & Then
            mockMvc.perform(MockMvcRequestBuilders.multipart("/api/board/")
                            .file(givenFile)
                            .param("category", boardCreateRequest.category())
                            .param("title", boardCreateRequest.title())
                            .param("content", boardCreateRequest.content())
                            .param("writer", boardCreateRequest.writer())
                            .param("uploadFile.width", String.valueOf(boardCreateRequest.uploadFile().width()))
                            .param("uploadFile.height", String.valueOf(boardCreateRequest.uploadFile().height()))
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            verify(boardService).create(boardCreateRequest);
        }

    }
}
