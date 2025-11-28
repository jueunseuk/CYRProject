package com.junsu.cyr.model.achievement;

import com.junsu.cyr.domain.achievements.AchievementReward;
import com.junsu.cyr.domain.achievements.RewardType;
import lombok.Data;

@Data
public class AchievementRewardResponse {
    private Long achievementRewardId;
    private RewardType rewardType;
    private Integer amount;

    public AchievementRewardResponse(AchievementReward achievementReward) {
        this.achievementRewardId = achievementReward.getAchievementRewardId();
        this.rewardType = achievementReward.getRewardType();
        this.amount = achievementReward.getAmount();
    }

}
