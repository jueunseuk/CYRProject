package com.junsu.cyr.domain.polls;

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
@Table(name = "poll_option")
public class PollOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_option_id", nullable = false)
    private Integer PollOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll")
    private Poll poll;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;
}
