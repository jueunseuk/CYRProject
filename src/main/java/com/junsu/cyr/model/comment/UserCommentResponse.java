package com.junsu.cyr.model.comment;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCommentResponse {
    private Long commentId;
    private String content;
    private String emoticonUrl;
    private LocalDateTime createdAt;

    private Integer userId;
    private String userName;

    private Long postId;
    private String title;
    private String author;
    private Long viewCnt;
    private String boardName;

    public UserCommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        if(comment.getEmoticon() != null) {
            this.emoticonUrl = comment.getEmoticon().getImageUrl();
        }
        this.createdAt = comment.getCreatedAt();

        User user = comment.getUser();
        Post post = comment.getPost();
        Board board = post.getBoard();

        this.userId = user.getUserId();
        this.userName = user.getNickname();

        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.author = post.getUser().getNickname();
        this.viewCnt = post.getViewCnt();
        this.boardName = board.getName();
    }
}
