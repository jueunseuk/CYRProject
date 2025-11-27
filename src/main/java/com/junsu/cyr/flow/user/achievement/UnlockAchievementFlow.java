package com.junsu.cyr.flow.user.achievement;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.AchievementReward;
import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.service.achievement.AchievementLogService;
import com.junsu.cyr.service.achievement.AchievementRewardService;
import com.junsu.cyr.service.achievement.AchievementService;
import com.junsu.cyr.service.notification.usecase.AchievementNotificationUseCase;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnlockAchievementFlow {

    private final AchievementService achievementService;
    private final AchievementRewardService achievementRewardService;
    private final AchievementLogService achievementLogService;
    private final AchievementNotificationUseCase achievementNotificationUseCase;
    private final UserService userService;

    @Transactional
    public void achievementFlow(User user, Type type, Scope scope, Long conditionAmount) {
        Achievement achievement = achievementService.getAchievement(type, scope, conditionAmount);
        if(achievement == null) {
            return;
        }

        if(achievementLogService.getAchievementLog(achievement, user) != null) {
            return;
        }

        achievementLogService.createAchievementLog(achievement, user);
        achievementNotificationUseCase.accomplishAchievement(user, achievement.getName());

        List<AchievementReward> achievementRewardList = achievementRewardService.getAchievementRewards(achievement);
        for(AchievementReward reward : achievementRewardList) {
            switch(reward.getRewardType()) {
                case SAND -> userService.addSand(user, 17, reward.getAmount());
                case GLASS -> userService.addGlass(user, 4, reward.getAmount());
            }
        }
        userService.addExperience(user, 9);
    }
}
