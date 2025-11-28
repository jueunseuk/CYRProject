package com.junsu.cyr.domain.polls;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.PollOptionCount;
import com.junsu.cyr.response.exception.code.PollExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "poll")
public class Poll extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_id", nullable = false)
    private Integer pollId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "closed_at", nullable = false)
    private LocalDateTime closedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_cnt")
    private Long userCnt;

    @Column(name = "winning_option_id")
    private Long winningOptionId;

    public void updateStatus(Status status) {
        if(status == null) {
            throw new BaseException(PollExceptionCode.INVALID_VALUE_INJECTION);
        }
        this.status = status;
    }

    public void updateResult(Long winningOptionId, Long userCnt) {
        this.winningOptionId = winningOptionId;
        this.userCnt = userCnt;
        this.status = Status.CLOSED;
    }

    public void updateImageUrl(String imageUrl) {
        if(imageUrl == null || imageUrl.isEmpty()) {
            throw new BaseException(PollExceptionCode.INVALID_VALUE_INJECTION);
        }
        this.imageUrl = imageUrl;
    }
}
