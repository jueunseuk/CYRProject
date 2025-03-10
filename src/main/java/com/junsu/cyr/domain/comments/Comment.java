package com.junsu.cyr.domain.comments;

import com.junsu.cyr.domain.posts.Post;
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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "comment_fixed", nullable = false)
    private Fixed commentFixed;

    @Column(name = "comment_created_at", updatable = false)
    private LocalDateTime commentCreatedAt;

    @Column(name = "comment_updated_at")
    private LocalDateTime commentUpdatedAt;
}
