package com.junsu.cyr.model.achievement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AchievementRefreshResponse {
    private Long achievementCount;
    private Long experience;
    private Long sand;
    private Long glass;

    public static AchievementRefreshResponse empty() {
        return new AchievementRefreshResponse(0L, 0L, 0L, 0L);
    }

    public void add(AchievementRefreshResponse response) {
        this.achievementCount += response.getAchievementCount();
        this.experience += response.getExperience();
        this.sand += response.getSand();
        this.glass += response.getGlass();
    }

    public void addAchievement() {
        this.achievementCount++;
    }

    public void addExperience(long amount) {
        this.experience += amount;
    }

    public void addSand(long amount) {
        this.sand += amount;
    }

    public void addGlass(long amount) {
        this.glass += amount;
    }
}
