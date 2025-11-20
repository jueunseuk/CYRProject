package com.junsu.cyr.domain.achievements;

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
@Table(name = "achievement_log")
public class AchievementLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_log_id")
    private Long achievementLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement")
    private Achievement achievement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
}
