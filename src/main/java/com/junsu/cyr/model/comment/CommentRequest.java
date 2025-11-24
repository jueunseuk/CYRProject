package com.junsu.cyr.model.comment;

import lombok.Data;

@Data
public class CommentRequest {
    private Long postId;
    private String comment;
    private Boolean locked;
    private Integer shopItemId;
}
