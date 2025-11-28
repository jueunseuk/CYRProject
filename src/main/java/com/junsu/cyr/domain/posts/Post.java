package com.junsu.cyr.domain.posts;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.BoardExceptionCode;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board")
    private Board board;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "view_cnt", nullable = false)
    private Long viewCnt;

    @Column(name = "comment_cnt", nullable = false)
    private Long commentCnt;

    @Column(name = "empathy_cnt", nullable = false)
    private Long empathyCnt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "locked")
    private Locked locked;

    public static Post create(User user, Board board, String title, String content, Locked locked) {
        if (user == null || user.getStatus() != Status.ACTIVE) {
            throw new BaseException(UserExceptionCode.NOT_EXIST_USER);
        }
        if (board == null) {
            throw new BaseException(BoardExceptionCode.NOT_FOUND_BOARD);
        }
        if (title == null || title.isBlank()) {
            throw new BaseException(PostExceptionCode.INVALID_VALUE_INJECTION);
        }
        if (content == null || content.isBlank()) {
            throw new BaseException(PostExceptionCode.INVALID_VALUE_INJECTION);
        }

        return Post.builder()
                .user(user)
                .board(board)
                .title(title)
                .content(content)
                .viewCnt(0L)
                .commentCnt(0L)
                .empathyCnt(0L)
                .locked(locked)
                .build();
    }

    public void update(String title, String content, Board board, Locked locked) {
        if (user == null || user.getStatus() != Status.ACTIVE) {
            throw new BaseException(UserExceptionCode.NOT_EXIST_USER);
        }
        if (board == null) {
            throw new BaseException(BoardExceptionCode.NOT_FOUND_BOARD);
        }
        if (title == null || title.isBlank()) {
            throw new BaseException(PostExceptionCode.INVALID_VALUE_INJECTION);
        }
        if (content == null || content.isBlank()) {
            throw new BaseException(PostExceptionCode.INVALID_VALUE_INJECTION);
        }

        this.title = title;
        this.content = content;
        this.board = board;
        this.locked = locked;
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseViewCnt() {
        this.viewCnt += 1;
    }

    public void increaseCommentCnt() {
        this.commentCnt += 1;
    }
    public void decreaseCommentCnt() {
        this.commentCnt -= 1;
        if(this.commentCnt < 0) {
            this.commentCnt = 0L;
        }
    }

    public void increaseEmpathyCnt() {
        this.empathyCnt += 1;
    }
    public void decreaseEmpathyCnt() {
        this.empathyCnt -= 1;
        if(this.empathyCnt < 0) {
            this.empathyCnt = 0L;
        }
    }
}
