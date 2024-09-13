package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDtoV1;
import com.ddalkkak.splitting.board.exception.UploadFileErrorCode;
import com.ddalkkak.splitting.board.exception.UploadFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileManager fileManager;

    public void isValid() {

    }


    public UploadFileCreateDto make(final BoardCreateRequest.FileUploadRequest fileUploadRequest){
        MultipartFile file = fileUploadRequest.files().get(0);

        String orginalName = file.getOriginalFilename();
        String fileName = orginalName.substring(orginalName.lastIndexOf("\\") + 1);
        String fileType = file.getContentType();

        if (!fileType.startsWith("image/")) {
            throw new UploadFileException.IsNotImageException(UploadFileErrorCode.CANNOT_BE_UPLOADED,1l);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Thumbnails.of(file.getInputStream())
                    .size(fileUploadRequest.width(), fileUploadRequest.height())
                    .toOutputStream(baos);
        }catch (IOException io){
            throw new UploadFileException.CannotBeUploadedException(UploadFileErrorCode.CANNOT_BE_UPLOADED,1l);
        }

        byte[] bytes  = baos.toByteArray();

        UploadFileCreateDto uploadFileCreateDto =  UploadFileCreateDto.of(fileName, fileType, bytes);

        return uploadFileCreateDto;
    }


}