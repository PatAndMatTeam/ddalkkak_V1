package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Builder
public class BoardCreateDto{
         String category;
         String title;
         String content;
         String writer;

    UploadFileCreateDto files;


    public static BoardCreateDto from(BoardCreateRequest createRequest){
        return BoardCreateDto.builder()
                .category(createRequest.category())
                .title(createRequest.title())
                .content(createRequest.content())
                .writer(createRequest.writer())
                .files(UploadFileCreateDto.fromMultipartFile(createRequest.uploadFile().files().get(0)))
                .build();
    }

    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .category(Category.valueOf(this.category))
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .files(List.of(this.files.toEntity()))
                .build();
    }

    public void addFiles(UploadFileCreateDto files){
        this.files = files;
    }
}
