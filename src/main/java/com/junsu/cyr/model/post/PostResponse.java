package com.junsu.cyr.model.post;

import com.junsu.cyr.domain.posts.Locked;
import com.junsu.cyr.domain.posts.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long postId;
    private Integer boardId;
    private String boardName;
    private String title;
    private String content;
    private Integer userId;
    private String userName;
    private Long viewCnt;
    private Long commentCnt;
    private Long empathyCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Locked locked;

    public PostResponse(Post post) {
        this.postId = post.getPostId();
        this.boardId = post.getBoard().getBoardId();
        this.boardName = post.getBoard().getName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getUserId();
        this.userName = post.getUser().getName();
        this.viewCnt = post.getViewCnt();
        this.commentCnt = post.getCommentCnt();
        this.empathyCnt = post.getEmpathyCnt();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.locked = post.getLocked();
    }
}
