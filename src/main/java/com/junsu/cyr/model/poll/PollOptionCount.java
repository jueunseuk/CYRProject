package com.junsu.cyr.model.poll;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PollOptionCount {
    private Long pollOptionId;
    private String content;
    private Long voteCount;
}
