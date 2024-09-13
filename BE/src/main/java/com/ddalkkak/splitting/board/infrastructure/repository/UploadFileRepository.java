package com.ddalkkak.splitting.board.infrastructure.repository;


import com.ddalkkak.splitting.board.infrastructure.entity.UploadFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFileEntity, Long> {

//    List<UploadFileEntity> findByBoardId(Long boardId);

    @Query("SELECT f FROM UploadFileEntity f WHERE f.board.id = :boardId")
    List<UploadFileEntity> findByBoardId(@Param("boardId") Long boardId);
}
