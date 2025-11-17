package com.junsu.cyr.domain.achievements;

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
@Table(name = "achievement_reward")
public class AchievementReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_reward_id")
    private Long achievementRewardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement")
    private Achievement achievement;

    @Column(name = "amount")
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_type")
    private RewardType rewardType;
}
