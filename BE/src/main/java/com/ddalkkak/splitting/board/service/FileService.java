package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
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
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileManager fileManager;

    public void isValid() {

    }

    public List<UploadFileCreateDto> make(final BoardCreateRequest createRequest){
        final List<MultipartFile> files = createRequest.files();
        final int width = createRequest.width();
        final int height = createRequest.height();

        List<UploadFileCreateDto> fileCreateDtos = Optional.ofNullable(files)
                .filter(f -> !f.isEmpty()) // 파일이 비어 있지 않을 때만 처리
                .map(file -> make(file, width, height))
                .orElse(List.of()); // 파일이 없으면 빈 리스트 반환

        return fileCreateDtos;
    }

    private List<UploadFileCreateDto> make(List<MultipartFile> files, int width, int height){
        // 파일 리스트가 null이거나 비어 있는 경우 빈 리스트 반환
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        List<UploadFileCreateDto> result = new ArrayList<>();

        for (MultipartFile file : files){
            String orginalName = file.getOriginalFilename();
            String fileName = orginalName.substring(orginalName.lastIndexOf("\\") + 1);
            String fileType = file.getContentType();

            if (!fileType.startsWith("image")){
                throw new UploadFileException.CannotBeUploadedException(UploadFileErrorCode.IS_NOT_IMAGE,1l);
            }

            log.info(fileType);
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