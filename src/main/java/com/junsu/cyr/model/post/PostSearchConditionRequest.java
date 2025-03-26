package com.junsu.cyr.model.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSearchConditionRequest {
    private String title;
    private String userId;
    private LocalDateTime start;
    private LocalDateTime end;

    private Integer page = 0;
    private Integer size = 20;

    private String sort = "createdAt";
    private String direction = "DESC";
}
