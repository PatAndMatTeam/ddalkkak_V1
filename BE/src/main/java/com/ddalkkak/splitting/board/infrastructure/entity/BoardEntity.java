package com.ddalkkak.splitting.board.infrastructure.entity;

import com.ddalkkak.splitting.board.domain.Board;
import com.ddalkkak.splitting.comment.instrastructure.entitiy.CommentEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="BOARD")
@Entity
public class BoardEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    @Builder.Default
    private Long recommend = 0L;

    @Builder.Default
    private Long leftVote = 0L;

    @Builder.Default
    private Long rightVote = 0L;

    @Builder.Default
    private Long visited = 0L;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String writer;

    @Builder.Default
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFileEntity> files = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // 외래 키로 사용될 부모의 ID
    private BoardEntity parent; // 부모 엔티티에 대한 참조

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent")  // 외래 키 설정
    private List<BoardEntity> children = new ArrayList<>();


    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeLeftVote(long leftVote){
        this.leftVote = leftVote;
    }

    public void changeRightVote(long rightVote){
        this.rightVote = rightVote;
    }

    public void changeVisited(long visited){
        this.visited = visited;
    }

    // Helper methods to manage replies
    public void addComment(CommentEntity comment) {
        getComments().add(comment);
        comment.setBoard(this);
    }
    public void increaseRecommend(){
        this.recommend++;
    }
    public void removeReply(CommentEntity reply) {
        comments.remove(reply);
    }

    public void addFile(UploadFileEntity file) {
        getFiles().add(file);

        file.addBoard(this);
    }

    public void addChild(BoardEntity board) {
        this.children.add(board);
        board.addParent(this);
    }

    public void addParent(BoardEntity board){
        this.parent = board;
    }


    public void removeFile(UploadFileEntity file) {
        files.remove(file);
    }


    public static BoardEntity fromModel(Board board){
        return BoardEntity.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .category(Category.fromValue(board.getCategory()))
                .writer(board.getWriter())
                .build();
    }

    public Board toModel(){
        return Board.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .recommend(this.recommend)
                .leftCnt(this.leftVote)
                .rightCnt(this.rightVote)
                .visited(this.visited)
                .category(this.category.toString())
                .modifiedDate(this.getLastModifiedDate())
                .writer(this.writer)
                .files(this.files == null ? null : this.files.stream().map(UploadFileEntity::toModel).collect(Collectors.toList()))
                .comments(this.comments == null ? null : this.comments.stream().map(CommentEntity::toModel).collect(Collectors.toList()))
                //.children(this.children == null ? null : this.children.stream().map(BoardEntity::toModel).collect(Collectors.toList()))
                .build();
    }

}
