package com.ddalkkak.splitting.board.domain;

import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.exception.UploadFileErrorCode;
import com.ddalkkak.splitting.board.exception.UploadFileException;
import lombok.Builder;
import lombok.Getter;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Getter
public class UploadFile {

    private final Long id;

    private final String fileTile;

    private final String fileName;

    private final String fileType;

    private final byte[] data;

    @Builder
    public UploadFile(Long id, String fileTile, String fileName, String fileType, byte[] data){
        this.id = id;
        this.fileTile = fileTile;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public static UploadFile from(MultipartFile file, FileCreateRequest fileCreate){
        String fileName = getFileName(file);
        String fileType = validFileType(file);

        return UploadFile.builder()
                .fileTile(fileCreate.getFileTitle())
                .data(makeThumbnail(file, fileCreate.getWidth(), fileCreate.getHeight()))
                .fileName(fileName)
                .fileType(fileType)
                .build();
    }

    public static UploadFile from(MultipartFile file){
        String fileName = getFileName(file);
        String fileType = validFileType(file);

        return UploadFile.builder()
                .fileTile(fileName)
                .data(makeThumbnail(file, 200, 200))
                .fileName(fileName)
                .fileType(fileType)
                .build();
    }

    private static byte[] makeThumbnail(MultipartFile file, int width, int height){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Thumbnails.of(file.getInputStream())
                    .size(width, height)
                    .toOutputStream(baos);
        }catch (IOException io){
            throw new UploadFileException.CannotBeUploadedException(UploadFileErrorCode.CANNOT_BE_UPLOADED,1l);
        }

        return baos.toByteArray();
    }

    private static String validFileType(MultipartFile file){
        String fileType = file.getContentType();
        if (!fileType.contains("image")){
            throw new UploadFileException.CannotBeUploadedException(UploadFileErrorCode.IS_NOT_IMAGE,1l);
        }
        return fileType;
    }

    private static String getFileName(MultipartFile file){
        String orginalName = file.getOriginalFilename();
        String fileName = orginalName.substring(orginalName.lastIndexOf("\\") + 1);

        return fileName;
    }
}
