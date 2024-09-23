package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;

import com.ddalkkak.splitting.board.api.request.FileUploadRequest;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardManager boardManager;
    @Autowired
    private FileManager fileManager;


    @DisplayName("게시글과 파일을 같이 등록할 수 있다.")
    @Test
    void registBoardAndFile() throws IOException {
        //given
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/testimage.jpg")) {
            if (inputStream == null) {
                throw new IOException("File not found in classpath");
            }
            //file
            MockMultipartFile givenFile = new MockMultipartFile("file", "image1.jpg",
                    "image/jpeg", inputStream );


            List<MockMultipartFile> files = new ArrayList<>();
            files.add(givenFile);

            FileUploadRequest fileUploadRequest = FileUploadRequest.builder()
                    .files(List.of(givenFile))
                    .width(100)
                    .height(100)
                    .build();

            //board
            BoardCreateRequest givenRequest = BoardCreateRequest.builder()
                    .title("갈라치기 해보자")
                    .content("어떻게 하면 잘 만들수 있을까?")
                    .category(Category.정치.name())
                    .writer("윤주영")
                    .files(List.of(givenFile))
                    .width(1)
                    .height(1)
                    .build();

            //when
            Long createdId = boardService.create(givenRequest);
            //then
            BoardDto result = boardService.read(createdId);

            assertEquals(givenRequest.title(), result.title());
            assertEquals(givenRequest.category(), result.category());
            assertEquals(givenRequest.content(), result.content());
            assertEquals(givenRequest.writer(), result.writer());
            assertEquals(givenRequest.files().get(0).getContentType(),
                    result.files().get(0).fileType());
            assertEquals(givenRequest.files().get(0).getOriginalFilename(),
                    result.files().get(0).fileName());
        }



    }
}
