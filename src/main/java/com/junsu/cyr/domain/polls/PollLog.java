package com.junsu.cyr.domain.polls;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_log_id")
    private Long pollLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll")
    private Poll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_option")
    private PollOption pollOption;
}
