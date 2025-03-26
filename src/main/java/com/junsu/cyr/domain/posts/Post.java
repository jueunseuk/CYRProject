package com.junsu.cyr.domain.posts;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "content", nullable = false)
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
}
