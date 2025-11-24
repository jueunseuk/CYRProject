package com.junsu.cyr.model.post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostSearchConditionRequest {
    private Integer boardId;
    private String start;
    private String end;

    private Integer page = 0;
    private Integer size = 20;

    private String sort = "createdAt";
    private String direction = "DESC";
}
