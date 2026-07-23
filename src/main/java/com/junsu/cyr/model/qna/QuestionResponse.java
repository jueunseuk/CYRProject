package com.junsu.cyr.model.qna;

import com.junsu.cyr.domain.qnas.Question;
import com.junsu.cyr.model.user.UserAuthorResponse;
import lombok.Data;

@Data
public class QuestionResponse {
    private Long questionId;
    private UserAuthorResponse user;
    private String title;
    private String content;
    private Integer sandCnt;
    private Integer answerCnt;

    public QuestionResponse(Question question) {
        this.questionId = question.getQuestionId();
        this.user = new UserAuthorResponse(question.getUser());
        this.title = question.getTitle();
        this.content = question.getContent();
        this.sandCnt = question.getSandCnt();
        this.answerCnt = question.getAnswerCnt();
    }
}
