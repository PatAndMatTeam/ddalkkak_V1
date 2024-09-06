package com.ddalkkak.splitting.board.infrastructure.repository;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
