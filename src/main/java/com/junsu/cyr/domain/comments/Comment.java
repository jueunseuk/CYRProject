package com.junsu.cyr.domain.comments;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "fixed", nullable = false)
    private Fixed fixed;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "locked")
    private Locked locked;

    public void update(String content, Locked locked) {
        this.content = content;
        this.locked = locked;
        this.updatedAt = LocalDateTime.now();
    }
}
