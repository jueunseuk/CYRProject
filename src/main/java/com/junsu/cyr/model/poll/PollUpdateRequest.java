package com.junsu.cyr.model.poll;

import com.junsu.cyr.domain.polls.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollUpdateRequest {
    private Status status;
}
