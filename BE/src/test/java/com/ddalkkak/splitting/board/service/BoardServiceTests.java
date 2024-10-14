package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.board.dto.BoardDto;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;


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

            //board
            BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                    .title("갈라치기 해보자")
                    .content("어떻게 하면 잘 만들수 있을까?")
                    .category(Category.정치.name())
                    .writer("윤주영")
                    .build();

            List<MultipartFile> multipartFiles = List.of(givenFile);
            List<FileCreateRequest> fileInfos = List.of(FileCreateRequest.builder()
                            .fileTitle("test")
                            .width(100)
                            .height(100)
                    .build());

            //when
            Long createdId = boardService.create(boardCreateRequest, multipartFiles, fileInfos);
            //then
            Board result = boardService.read(createdId);

            assertEquals(boardCreateRequest.getTitle(), result.getTitle());
            assertEquals(boardCreateRequest.getCategory(), result.getCategory());
            assertEquals(boardCreateRequest.getContent(), result.getContent());
            assertEquals(boardCreateRequest.getWriter(), result.getWriter());
            assertEquals(multipartFiles.get(0).getContentType(),
                    result.getFiles().get(0).getFileType());
            assertEquals(multipartFiles.get(0).getOriginalFilename(),
                    result.getFiles().get(0).getFileName());
        }



    }
}
