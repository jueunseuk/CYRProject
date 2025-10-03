package com.junsu.cyr.domain.glass;

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
@Table(name = "glass_log")
public class GlassLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "glass_log_id")
    private Long glassLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass")
    private Glass glass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "after", nullable = false)
    private Integer after;
}
