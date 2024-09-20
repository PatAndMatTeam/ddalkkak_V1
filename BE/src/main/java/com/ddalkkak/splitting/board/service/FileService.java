package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDtoV1;
import com.ddalkkak.splitting.board.dto.UploadFileDto;
import com.ddalkkak.splitting.board.exception.UploadFileErrorCode;
import com.ddalkkak.splitting.board.exception.UploadFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileManager fileManager;

    public void isValid() {

    }


    public List<UploadFileCreateDto> make(List<MultipartFile> files, int width, int height){
        // 파일 리스트가 null이거나 비어 있는 경우 빈 리스트 반환
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        List<UploadFileCreateDto> result = new ArrayList<>();

        for (MultipartFile file : files){
            String orginalName = file.getOriginalFilename();
            String fileName = orginalName.substring(orginalName.lastIndexOf("\\") + 1);
            String fileType = file.getContentType();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                Thumbnails.of(file.getInputStream())
                        .size(width, height)
                        .toOutputStream(baos);
            }catch (IOException io){
                throw new UploadFileException.CannotBeUploadedException(UploadFileErrorCode.CANNOT_BE_UPLOADED,1l);
            }

            byte[] bytes  = baos.toByteArray();

            UploadFileCreateDto uploadFileCreateDto =  UploadFileCreateDto.of(fileName, fileType, bytes);
            result.add(uploadFileCreateDto);
        }
            return result;
    }


}