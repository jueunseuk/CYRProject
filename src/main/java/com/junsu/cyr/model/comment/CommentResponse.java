package com.junsu.cyr.model.comment;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean fixed;
    private Boolean locked;

    private Integer userId;
    private String userName;
    private String profileUrl;

    private Long postId;
    private String title;
    private Long viewCnt;

    public CommentResponse(Comment comment, User user, Post post) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.userId = user.getUserId();
        this.userName = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.viewCnt = post.getViewCnt();
        this.fixed = comment.getFixed();
        this.locked = comment.getLocked();
    }
}
