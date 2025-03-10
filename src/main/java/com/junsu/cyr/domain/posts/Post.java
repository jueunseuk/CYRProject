package com.junsu.cyr.domain.posts;

import com.junsu.cyr.domain.boards.Board;
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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long post_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "post_title", nullable = false, length = 1000)
    private String postTitle;

    @Column(name = "post_content", nullable = false)
    private String postContent;

    @Column(name = "post_view", nullable = false)
    private Long postView;

    @Column(name = "post_comment_cnt", nullable = false)
    private Long postCommentCnt;

    @Column(name = "post_empathy_cnt", nullable = false)
    private Long postEmpathyCnt;

    @Column(name = "post_created_at", nullable = false, updatable = false)
    private LocalDateTime postCreatedAt;

    @Column(name = "post_updated_at")
    private LocalDateTime postUpdatedAt;
}
