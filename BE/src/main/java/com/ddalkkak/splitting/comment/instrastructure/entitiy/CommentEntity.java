package com.ddalkkak.splitting.comment.instrastructure.entitiy;

import com.ddalkkak.splitting.board.infrastructure.entity.BaseTimeEntity;
import com.ddalkkak.splitting.board.infrastructure.entity.BoardEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name="Comment")
@Entity
public class CommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    private String content;

    private String ip;

    private String userId;

    private String userPw;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 연관 관계의 주인 역할을 수행
    private BoardEntity board;

}
