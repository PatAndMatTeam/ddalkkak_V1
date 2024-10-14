package com.ddalkkak.splitting.board.infrastructure.repository;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;

public interface BoardCustomRepository {
    BoardEntity findByCategory(final String category, final Long id);
}
