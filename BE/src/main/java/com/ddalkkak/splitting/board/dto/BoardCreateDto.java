package com.ddalkkak.splitting.board.dto;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
public class BoardCreateDto{
         String category;
         String title;
         String content;
         String writer;

    List<UploadFileCreateDto> files;


    public static BoardCreateDto from(BoardCreateRequest createRequest){
        return BoardCreateDto.builder()
                .category(createRequest.getCategory())
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .writer(createRequest.getWriter())
                .build();
    }

    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .category(Category.valueOf(this.category))
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .build();
    }

    public void addFiles(List<UploadFileCreateDto> files){
        this.files = files;
    }


    private static boolean isInFile(FileCreateRequest fileCreateRequest){
        if (fileCreateRequest.getFiles()==null){
            return false;
        }
        return true;
    }
}
