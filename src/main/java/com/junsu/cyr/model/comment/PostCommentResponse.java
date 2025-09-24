package com.junsu.cyr.model.comment;

import lombok.Data;

import java.util.List;

@Data
public class PostCommentResponse {
    private List<CommentResponse> comments;

    public PostCommentResponse(List<CommentResponse> comments) {
        this.comments = comments;
    }
}
