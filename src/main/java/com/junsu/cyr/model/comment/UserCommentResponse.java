package com.junsu.cyr.model.comment;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCommentResponse {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;

    private Integer userId;
    private String userName;

    private Long postId;
    private String title;
    private Long viewCnt;

    public UserCommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();

        User user = comment.getUser();
        Post post = comment.getPost();

        this.userId = user.getUserId();
        this.userName = user.getNickname();

        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.viewCnt = post.getViewCnt();
    }
}
