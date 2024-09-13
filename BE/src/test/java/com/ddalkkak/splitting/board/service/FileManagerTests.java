package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.UploadFileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@ActiveProfiles("test")
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
        UploadFileEntity actual = fileManager.create(expected);

        //then
//        assertEquals(expected.fileName(), actual.getFileName());
//        assertEquals(expected.fileType(), actual.getFileType());
//        assertEquals(expected.data().length, actual.getData().length);

    }
}
