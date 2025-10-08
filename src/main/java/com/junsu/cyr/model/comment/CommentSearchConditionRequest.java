package com.junsu.cyr.model.comment;

import lombok.Data;

@Data
public class CommentSearchConditionRequest {
    private Integer page = 0;
    private Integer size = 20;

    private String sort = "createdAt";
    private String direction = "DESC";
}
