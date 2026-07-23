package com.junsu.cyr.model.qna;

import com.junsu.cyr.domain.qnas.Answer;
import com.junsu.cyr.model.user.UserAuthorResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerResponse {
    private Long answerId;
    private UserAuthorResponse user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime adoptedAt;

    public AnswerResponse(Answer answer) {
        this.answerId = answer.getAnswerId();
        this.user = new UserAuthorResponse(answer.getUser());
        this.content = answer.getContent();
        this.createdAt = answer.getCreatedAt();
        this.adoptedAt = answer.getAdoptedAt();
    }
}
