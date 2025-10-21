package com.junsu.cyr.model.poll;

import com.junsu.cyr.domain.polls.PollOption;
import lombok.Data;

@Data
public class PollOptionResponse {
    private Long pollOptionId;
    private String content;
    private Long voteCount;

    public PollOptionResponse(PollOption pollOption) {
        this.pollOptionId = pollOption.getPollOptionId();
        this.content = pollOption.getContent();
        this.voteCount = pollOption.getVoteCount();
    }
}
