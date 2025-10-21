package com.junsu.cyr.model.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.Status;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PollResponse {
    private Integer pollId;
    private String title;
    private String description;
    private String imageUrl;
    private Long userCnt;
    private Long winningOptionId;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private Status status;

    private Integer userId;
    private String nickname;

    private List<PollOptionResponse> options;

    private Boolean isJoin;
    private Long votePollOptionId;

    public PollResponse(Poll poll, List<PollOptionResponse> options) {
        this.pollId = poll.getPollId();
        this.title = poll.getTitle();
        this.description = poll.getDescription();
        this.imageUrl = poll.getImageUrl();
        this.createdAt = poll.getCreatedAt();
        this.closedAt = poll.getClosedAt();
        this.status = poll.getStatus();
        this.userCnt = poll.getUserCnt();
        this.winningOptionId = poll.getWinningOptionId();

        User user = poll.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();

        this.options = options;

        this.isJoin = false;
        this.votePollOptionId = null;
    }

    public PollResponse(Poll poll) {
        this.pollId = poll.getPollId();
        this.title = poll.getTitle();
        this.description = poll.getDescription();
        this.imageUrl = poll.getImageUrl();
        this.createdAt = poll.getCreatedAt();
        this.closedAt = poll.getClosedAt();
        this.status = poll.getStatus();
        this.userCnt = poll.getUserCnt();
        this.winningOptionId = poll.getWinningOptionId();

        User user = poll.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
    }
}
