package com.ddalkkak.splitting.board.infrastructure.repository;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.infrastructure.entity.QBoardEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardCustomRepositoryImpl implements  BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QBoardEntity board = QBoardEntity.boardEntity;

    @Override
    public BoardEntity findByCategory(final String category, final Long id) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.category.eq(Category.valueOf(category))
                        .and(board.parent.id.eq(id)))
                .fetchOne();
    }
}
