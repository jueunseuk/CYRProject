package com.junsu.cyr.model.comment;

import com.junsu.cyr.domain.comments.Locked;
import lombok.Data;

@Data
public class CommentRequest {
    private Long postId;
    private String comment;
    private Locked locked;
}
