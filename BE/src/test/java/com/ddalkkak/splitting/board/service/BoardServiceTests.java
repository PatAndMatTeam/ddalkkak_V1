package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardRecommendUpdateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
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
import java.util.Optional;

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
                    "image/jpeg", inputStream);


            List<MockMultipartFile> files = new ArrayList<>();
            files.add(givenFile);

            //board
            BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                    .title("갈라치기 해보자")
                    .content("어떻게 하면 잘 만들수 있을까?")
                    .category(Category.정치.name)
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
            assertEquals(Category.fromValue(boardCreateRequest.getCategory()).toString(), result.getCategory());
            assertEquals(boardCreateRequest.getContent(), result.getContent());
            assertEquals(boardCreateRequest.getWriter(), result.getWriter());
            assertEquals(multipartFiles.get(0).getContentType(),
                    result.getFiles().get(0).getFileType());
            assertEquals(multipartFiles.get(0).getOriginalFilename(),
                    result.getFiles().get(0).getFileName());
        }
    }

    @DisplayName("게시글의 조회수를 증가할 수 있다.")
    @Test
    void visited() throws IOException {
        //given
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/testimage.jpg")) {
            if (inputStream == null) {
                throw new IOException("File not found in classpath");
            }
            //file
            MockMultipartFile givenFile = new MockMultipartFile("file", "image1.jpg",
                    "image/jpeg", inputStream);


            List<MockMultipartFile> files = new ArrayList<>();
            files.add(givenFile);

            //board
            BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                    .title("갈라치기 해보자")
                    .content("어떻게 하면 잘 만들수 있을까?")
                    .category(Category.정치.name)
                    .writer("윤주영")
                    .build();

            List<MultipartFile> multipartFiles = List.of(givenFile);
            List<FileCreateRequest> fileInfos = List.of(FileCreateRequest.builder()
                    .fileTitle("test")
                    .width(100)
                    .height(100)
                    .build());


            Long createdId = boardService.create(boardCreateRequest, multipartFiles, fileInfos);

            //when
            boardService.visit(createdId);

            //then
            Board read = boardService.read(createdId);

            assertEquals(1, read.getVisited());
        }
    }
    @DisplayName("게시글의 파일을 변경할 수 있다.")
    @Test
    void changeFile() throws IOException {
        //given
        try (InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream("images/testimage.jpg");
            InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("images/testimage.jpg")) {
            if (inputStream1 == null || inputStream2 == null) {
                throw new IOException("File not found in classpath");
            }
            //file
            MockMultipartFile givenFile1 = new MockMultipartFile("file", "image1.jpg",
                    "image/jpeg", inputStream1);

            MockMultipartFile givenFile2 = new MockMultipartFile("file", "update.jpg",
                    "image/jpeg", inputStream2);
            List<MultipartFile> updateFiles = List.of(givenFile2);

            //board
            BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                    .title("갈라치기 해보자")
                    .content("어떻게 하면 잘 만들수 있을까?")
                    .category(Category.정치.name)
                    .writer("윤주영")
                    .build();

            List<MultipartFile> multipartFiles = List.of(givenFile1);
            List<FileCreateRequest> fileInfos = List.of(FileCreateRequest.builder()
                    .fileTitle("test")
                    .width(100)
                    .height(100)
                    .build());

            Long createdId = boardService.create(boardCreateRequest, multipartFiles, fileInfos);

            BoardUpdateRequest updateBoard = BoardUpdateRequest.builder()
                    .build();

            boardService.update(createdId, updateBoard, Optional.of(updateFiles), fileInfos);

            Board read = boardService.read(createdId);

            assertEquals(givenFile2.getOriginalFilename(), read.getFiles().get(0).getFileName());
            assertEquals(1, read.getFiles().size());
        }
    }


    @DisplayName("게시글의 좌우 사진 투표수를 변경할 수 있다.")
    @Test
    void updateRecommend() throws IOException {
        //given
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/testimage.jpg")) {
            if (inputStream == null) {
                throw new IOException("File not found in classpath");
            }
            //file
            MockMultipartFile givenFile = new MockMultipartFile("file", "image1.jpg",
                    "image/jpeg", inputStream);


            List<MockMultipartFile> files = new ArrayList<>();
            files.add(givenFile);

            //board
            BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                    .title("갈라치기 해보자")
                    .content("어떻게 하면 잘 만들수 있을까?")
                    .category(Category.정치.name)
                    .writer("윤주영")
                    .build();

            List<MultipartFile> multipartFiles = List.of(givenFile);
            List<FileCreateRequest> fileInfos = List.of(FileCreateRequest.builder()
                    .fileTitle("test")
                    .width(100)
                    .height(100)
                    .build());

            Long createdId = boardService.create(boardCreateRequest, multipartFiles, fileInfos);



            BoardRecommendUpdateRequest request = BoardRecommendUpdateRequest.builder()
                    .leftRecommend(2)
                    .rightRecommend(1)
                    .build();
            //when
            boardService.update(createdId,request);
            Board read = boardService.read(createdId);

            //then
            assertEquals(request.leftRecommend(), read.getLeftCnt());
            assertEquals(request.rightRecommend(), read.getRightCnt());
        }
    }
}
