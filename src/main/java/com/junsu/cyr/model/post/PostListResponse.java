package com.junsu.cyr.model.post;

import com.junsu.cyr.domain.posts.Locked;
import com.junsu.cyr.domain.posts.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostListResponse {
    private Long postId;
    private String title;
    private Integer boardId;
    private String boardName;
    private String boardKorean;
    private Integer userId;
    private String userNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCnt;
    private Long commentCnt;
    private Long empathyCnt;
    private Locked locked;

    public PostListResponse(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.boardId = post.getBoard().getBoardId();
        this.boardName = post.getBoard().getName();
        this.boardKorean = post.getBoard().getKorean();
        this.userId = post.getUser().getUserId();
        this.userNickname = post.getUser().getNickname();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.viewCnt = post.getViewCnt();
        this.commentCnt = post.getCommentCnt();
        this.empathyCnt = post.getEmpathyCnt();
        this.locked = post.getLocked();
    }
}
