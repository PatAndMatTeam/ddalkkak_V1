package com.ddalkkak.splitting.board.infrastructure.repository;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>, BoardCustomRepository {

    @EntityGraph(attributePaths = {"files", "comments"}) // "files" 속성도 함께 로드
    Optional<BoardEntity> findById(Long id);

    Page<BoardEntity> findByCategory(Category category, Pageable pageable);

    List<BoardEntity> findByCategoryAndParentId(Category category, Long parentId, Pageable pageable);
}
