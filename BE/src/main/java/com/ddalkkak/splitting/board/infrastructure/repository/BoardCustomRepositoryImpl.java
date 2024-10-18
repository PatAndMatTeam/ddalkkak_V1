package com.ddalkkak.splitting.board.infrastructure.repository;

import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.infrastructure.entity.QBoardEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardCustomRepositoryImpl implements  BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    QBoardEntity board = QBoardEntity.boardEntity;

    @Override
    public BoardEntity findByCategory(final String category, final Long id) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.category.eq(Category.valueOf(category))
                        .and(board.id.eq(id)))
                .fetchOne();
    }

    @Override
    public List<BoardEntity> findByCategoryAndParentId(Category category, Long parentId, Pageable pageable) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.parent.id.eq(parentId).and(board.category.eq(category)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }
}
