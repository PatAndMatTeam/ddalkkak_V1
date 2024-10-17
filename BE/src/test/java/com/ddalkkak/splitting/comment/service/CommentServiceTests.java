package com.ddalkkak.splitting.comment.service;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.FileCreateRequest;
import com.ddalkkak.splitting.board.infrastructure.entity.Category;
import com.ddalkkak.splitting.board.service.BoardService;
import com.ddalkkak.splitting.comment.api.reqeust.CommentCreateRequest;
import com.ddalkkak.splitting.comment.api.reqeust.CommentDeleteRequest;
import com.ddalkkak.splitting.comment.exception.CommentErrorCode;
import com.ddalkkak.splitting.comment.exception.CommentException;
import com.ddalkkak.splitting.config.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestSecurityConfig.class) // 테스트용 보안 구성 클래스 추가
@ActiveProfiles("test")
@SpringBootTest
public class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardService boardService;

    @DisplayName("댓글을 작성할 수 있다.")
    @Test
    void registComment(){
        //given
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .category(Category.축구.name)
                .title("손흥민 화이팅")
                .content("월드컵 우승하자")
                .writer("윤주영")
                .build();
        List<MultipartFile> multipartFiles1 = List.of();
        List<FileCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(boardCreateRequest, multipartFiles1, fileInfos1);

        CommentCreateRequest commentCreateRequest = CommentCreateRequest.builder()
                .writer("yjy")
                .password("1234")
                .content("ㅋㅋㅋㅋ")
                .parentId(0L)
                .build();

        //when
        Long createdId = commentService.create(boardId, commentCreateRequest);

        //then
        assertNotNull(createdId);
        assertNotEquals(0L, createdId);
    }

    @DisplayName("비밀번호가 같은 댓글 삭제시 성공한다.")
    @Test
    void deleteCommentMatchPassword(){
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .category(Category.축구.name)
                .title("손흥민 화이팅")
                .content("월드컵 우승하자")
                .writer("윤주영")
                .build();
        List<MultipartFile> multipartFiles1 = List.of();
        List<FileCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(boardCreateRequest, multipartFiles1, fileInfos1);

        CommentCreateRequest originComment = CommentCreateRequest.builder()
                .writer("yjy")
                .password("1234")
                .content("ㅋㅋㅋㅋ")
                .parentId(0L)
                .build();

        Long createdId = commentService.create(boardId, originComment);


        //given
        CommentDeleteRequest request= CommentDeleteRequest.builder()
                .password(originComment.password())
                .build();

        //when
        assertDoesNotThrow(() ->  commentService.delete(createdId, request));
    }

    @DisplayName("비밀번호가 다른 댓글 삭제시 실패한다.")
    @Test
    void deleteCommentNotMatchPassword(){
        BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
                .category(Category.축구.name)
                .title("손흥민 화이팅")
                .content("월드컵 우승하자")
                .writer("윤주영")
                .build();
        List<MultipartFile> multipartFiles1 = List.of();
        List<FileCreateRequest> fileInfos1 = List.of();

        Long boardId = boardService.create(boardCreateRequest, multipartFiles1, fileInfos1);

        CommentCreateRequest originComment = CommentCreateRequest.builder()
                .writer("yjy")
                .password("1234")
                .content("ㅋㅋㅋㅋ")
                .parentId(0L)
                .build();

        Long createdId = commentService.create(boardId, originComment);

        //given
        CommentDeleteRequest request= CommentDeleteRequest.builder()
                .password("틀린비밀번호")
                .build();

        //when
        // then
        CommentException.NotMatchPasswordException exception = assertThrows(
                CommentException.NotMatchPasswordException.class,
                () -> commentService.delete(createdId, request)
        );

        assertEquals(CommentErrorCode.NOT_MATCH_PASSWORD.getMessage(), exception.getErrorCode().getMessage());
    }

}
