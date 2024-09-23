package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.dto.UploadFileDto;
import com.ddalkkak.splitting.board.infrastructure.repository.UploadFileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("test")
@SpringBootTest
public class FileManagerTests {

    @Autowired
    private FileManager fileManager;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @DisplayName("파일을 생성할 수 있다.")
    @Test
    public void testCreateFile() {
        //given
        UploadFileCreateDto expected = UploadFileCreateDto.builder()
                .fileName("testFile.jpg")
                .fileType("image/jpeg")
                .data(new byte[]{1, 2, 3, 4, 5})
                .build();

        //when
        UploadFileDto actual = fileManager.create(expected);

        //then
        assertEquals(expected.getFileName(), actual.fileName());
        assertEquals(expected.getFileType(), actual.fileType());
        assertEquals(expected.getData().length, actual.data().length);

    }
}
