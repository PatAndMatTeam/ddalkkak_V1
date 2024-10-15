package com.ddalkkak.splitting.board.infrastructure.repository;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardCustomRepository {
    BoardEntity findByCategory(final String category, final Long id);

    List<BoardEntity> findByCategoryAndParentId(Category category, Long parentId, Pageable pageable);
}
