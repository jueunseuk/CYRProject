package com.junsu.cyr.model.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSearchConditionRequest {
    private Integer boardId;
    private String title;
    private String userNickname;
    private String start;
    private String end;

    private Integer page = 0;
    private Integer size = 20;

    private String sort = "createdAt";
    private String direction = "DESC";
}
