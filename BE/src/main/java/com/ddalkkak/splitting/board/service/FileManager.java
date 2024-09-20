package com.ddalkkak.splitting.board.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.dto.UploadFileCreateDto;
import com.ddalkkak.splitting.board.dto.UploadFileDto;
import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import com.ddalkkak.splitting.board.infrastructure.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@RequiredArgsConstructor
@Service
public class FileManager {

    private final UploadFileRepository uploadFileRepository;

    public void read(Long id){
        uploadFileRepository.findById(id);
    }

    public UploadFileEntity readBoardId(Long id){
        return uploadFileRepository.findByBoardId(id).get(0);
    }

    public UploadFileDto create(UploadFileCreateDto uploadFileCreateDto) {
        return UploadFileDto.from(uploadFileRepository
                .save(uploadFileCreateDto.toEntity()));
    }

    public void update(){

    }

    public void delete(){

    }

//    public void create(BoardCreateRequest.FileUploadRequest fileUploadRequest)  {
//
//    }


}
