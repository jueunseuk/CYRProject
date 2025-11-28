package com.junsu.cyr.model.achievement;

import com.junsu.cyr.domain.achievements.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AchievementLogResponse {
    private Long achievementLogId;
    private String name;
    private String description;
    private String imageUrl;
    private Type type;
    private Scope scope;
    private Difficulty difficulty;
    private LocalDateTime createdAt;
    private List<AchievementRewardResponse> achievementRewards;

    public AchievementLogResponse(AchievementLog achievementLog, List<AchievementRewardResponse> achievementRewards) {
        this.achievementLogId = achievementLog.getAchievementLogId();
        this.createdAt = achievementLog.getCreatedAt();
        Achievement achievement = achievementLog.getAchievement();
        this.name = achievement.getName();
        this.description = achievement.getDescription();
        this.imageUrl = achievement.getImageUrl();
        this.type = achievement.getType();
        this.scope = achievement.getScope();
        this.difficulty = achievement.getDifficulty();
        this.achievementRewards = achievementRewards;
    }
}
