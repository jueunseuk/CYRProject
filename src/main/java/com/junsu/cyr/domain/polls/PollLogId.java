package com.junsu.cyr.domain.polls;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PollLogId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "poll_id")
    private Integer pollId;
}
