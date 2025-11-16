package com.junsu.cyr.service.achievement;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.AchievementReward;
import com.junsu.cyr.repository.AchievementRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementRewardService {

    private final AchievementRewardRepository achievementRewardRepository;

    public List<AchievementReward> getAchievementRewards(Achievement achievement) {
        return achievementRewardRepository.findAllByAchievement(achievement);
    }
}
