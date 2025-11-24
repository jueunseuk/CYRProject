package com.junsu.cyr.model.achievement;

import com.junsu.cyr.domain.achievements.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    public AchievementLogResponse(AchievementLog achievementLog) {
        this.achievementLogId = achievementLog.getAchievementLogId();
        this.createdAt = achievementLog.getCreatedAt();
        Achievement achievement = achievementLog.getAchievement();
        this.name = achievement.getName();
        this.description = achievement.getDescription();
        this.imageUrl = achievement.getImageUrl();
        this.type = achievement.getType();
        this.scope = achievement.getScope();
        this.difficulty = achievement.getDifficulty();
    }
}
