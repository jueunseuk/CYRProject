package com.junsu.cyr.domain.achievements;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id", nullable = false)
    private Integer achievementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_next")
    private Achievement achievementNext;

    @Column(name = "name", nullable = false, length = 1000)
    private String name;

    @Column(name = "badge_url")
    private String badgeUrl;

    @Column(name = "reward_sand")
    private Integer rewardSand;

    @Column(name = "condition_number")
    private Integer conditionNumber;

    @Column(name = "condition_date")
    private LocalDateTime conditionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false)
    private ConditionType conditionType;
}
