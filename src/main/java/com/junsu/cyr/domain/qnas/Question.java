package com.junsu.cyr.domain.qnas;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.QnaExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Question extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "sand_cnt")
    private Integer sandCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "answer_cnt")
    private Integer answerCnt;

    public static Question of(User user, String title, String content, Integer sandCnt) {
        validateTitle(title);
        validateContent(content);
        validateSandCnt(sandCnt);

        return Question.builder()
                .user(user)
                .title(title)
                .content(content)
                .sandCnt(sandCnt)
                .status(Status.OPEN)
                .answerCnt(0)
                .build();
    }

    public Boolean isClosed() {
        return this.getStatus() == Status.CLOSED;
    }

    public void increaseAnswerCnt() {
        this.answerCnt++;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    private static void validateTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(QnaExceptionCode.INVALID_TITLE);
        }
    }

    private static void validateContent(String content) {
        if(content == null || content.isEmpty()) {
            throw new BaseException(QnaExceptionCode.INVALID_CONTENT);
        }
    }

    private static void validateSandCnt(Integer sandCnt) {
        if(sandCnt == null || sandCnt < 1) {
            throw new BaseException(QnaExceptionCode.INVALID_SAND_CNT);
        }
    }
}
