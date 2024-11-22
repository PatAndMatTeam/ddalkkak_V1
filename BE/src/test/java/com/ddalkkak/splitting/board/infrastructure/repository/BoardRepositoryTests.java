package com.ddalkkak.splitting.board.infrastructure.repository;


import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.common.config.QueryDslConfig;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(QueryDslConfig.class)
@DataJpaTest
@Transactional
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("V1: 부모id로 자식을 조회할 수 있다.")
    @Test
    void getChildsV1(){
        BoardEntity board = BoardEntity.builder()
                .files(List.of())
                .comments(List.of())
                .content("친추 yjy하셈")
                .title("게임 같이할 뿐")
                .writer("윤주영")
                .category(Category.기타_게임)
                .build();

       Long createdId = boardRepository.save(board).getId();

        BoardEntity parent = boardRepository.findById(createdId).get();

        BoardEntity son = BoardEntity.builder()
                .files(List.of())
                .comments(List.of())
                .title("자식")
                .content("자식")
                .writer("자식")
                .category(Category.기타_게임)
                .build();
        parent.addChild(son);

        boardRepository.save(parent);

        List<BoardEntity> childs = boardRepository.findById(createdId).get().getChildren();

        for (int i=0; i<childs.size(); i++){
            assertEquals(son.getTitle(), childs.get(i).getTitle());
            assertEquals(son.getContent(), childs.get(i).getContent());
            assertEquals(son.getWriter(), childs.get(i).getWriter());
            assertEquals(son.getCategory(), childs.get(i).getCategory());
        }
    }

    @DisplayName("V2: 부모id로 자식을 조회할 수 있다.")
    @Test
    void getChildsV2(){
        BoardEntity board = BoardEntity.builder()
                .files(List.of())
                .comments(List.of())
                .content("친추 yjy하셈")
                .title("게임 같이할 뿐")
                .writer("윤주영")
                .category(Category.기타_게임)
                .build();

        Long createdId = boardRepository.save(board).getId();

        BoardEntity parent = boardRepository.findById(createdId).get();

        BoardEntity son = BoardEntity.builder()
                .files(List.of())
                .comments(List.of())
                .title("자식")
                .content("자식")
                .writer("자식")
                .category(Category.기타_게임)
                .build();
        parent.addChild(son);

        boardRepository.save(parent);

        List<BoardEntity> childs = boardRepository.findByCategoryAndParentId(son.getCategory(),
                createdId,
                Pageable.ofSize(10));

        for (int i=0; i<childs.size(); i++){
            assertEquals(son.getTitle(), childs.get(i).getTitle());
            assertEquals(son.getContent(), childs.get(i).getContent());
            assertEquals(son.getWriter(), childs.get(i).getWriter());
            assertEquals(son.getCategory(), childs.get(i).getCategory());
        }
    }


}
