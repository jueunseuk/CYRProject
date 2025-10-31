package com.junsu.cyr.model.search;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class SearchResponse {
    private final Integer userId;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final String type;

    public SearchResponse(Integer userId, String nickname, LocalDateTime createdAt, String type) {
        this.userId = userId;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.type = type;
    }
}
