package com.junsu.cyr.model.poll;

import com.junsu.cyr.domain.polls.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollUpdateRequest {
    private String title;
    private String description;
    private LocalDateTime closedAt;
    private Status status;
}
