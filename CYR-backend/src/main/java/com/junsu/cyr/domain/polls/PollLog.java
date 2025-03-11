package com.junsu.cyr.domain.polls;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "poll_log")
public class PollLog extends BaseTime {
    @EmbeddedId
    @Column(name = "poll_log_id", nullable = false)
    private PollLogId pollLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_option_id")
    private PollOption pollOption;
}
