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

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class Answer extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question")
    private Question question;

    @Column(name = "content")
    private String content;

    @Column(name = "adopted_at")
    private LocalDateTime adoptedAt;

    public static Answer of(User user, Question question, String content) {
        validateContent(content);

        return Answer.builder()
                .user(user)
                .question(question)
                .content(content)
                .adoptedAt(null)
                .build();
    }

    public void adopt() {
        this.adoptedAt = LocalDateTime.now();
    }

    private static void validateContent(String content) {
        if(content == null || content.isEmpty()) {
            throw new BaseException(QnaExceptionCode.INVALID_CONTENT);
        }
    }
}
