package com.junsu.cyr.model.qna;

import com.junsu.cyr.domain.qnas.Question;
import com.junsu.cyr.domain.qnas.Status;
import com.junsu.cyr.model.user.UserAuthorResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionResponse {
    private Long questionId;
    private UserAuthorResponse user;
    private String title;
    private String content;
    private Integer sandCnt;
    private Integer answerCnt;
    private Status status;
    private LocalDateTime createdAt;

    public QuestionResponse(Question question) {
        this.questionId = question.getQuestionId();
        this.user = new UserAuthorResponse(question.getUser());
        this.title = question.getTitle();
        this.content = question.getContent();
        this.sandCnt = question.getSandCnt();
        this.answerCnt = question.getAnswerCnt();
        this.status = question.getStatus();
        this.createdAt = question.getCreatedAt();
    }
}
