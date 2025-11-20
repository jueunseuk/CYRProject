package com.junsu.cyr.domain.sand;

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
@Table(name = "sand_log")
public class SandLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sand_log_id", nullable = false)
    private Long sandLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sand")
    private Sand sand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "after", nullable = false)
    private Integer after;

    @Column(name = "delta")
    private Integer delta;
}
