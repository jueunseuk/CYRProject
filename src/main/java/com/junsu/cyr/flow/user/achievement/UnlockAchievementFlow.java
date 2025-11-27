package com.junsu.cyr.flow.user.achievement;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.AchievementReward;
import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.service.achievement.AchievementLogService;
import com.junsu.cyr.service.achievement.AchievementRewardService;
import com.junsu.cyr.service.achievement.AchievementService;
import com.junsu.cyr.service.experience.ExperienceService;
import com.junsu.cyr.service.glass.GlassService;
import com.junsu.cyr.service.notification.usecase.AchievementNotificationUseCase;
import com.junsu.cyr.service.sand.SandService;
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
    private final SandService sandService;
    private final GlassService glassService;
    private final ExperienceService experienceService;

    @Transactional
    public void unlockAchievement(User user, Type type, Scope scope, Long conditionAmount) {
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
        Sand sand = sandService.getSand(17);
        Glass glass = glassService.getGlass(4);
        for(AchievementReward reward : achievementRewardList) {
            switch(reward.getRewardType()) {
                case SAND -> {
                    user.updateSand(reward.getAmount());
                    sandService.createSandLog(sand, reward.getAmount(), user);
                }
                case GLASS -> {
                    user.updateGlass(reward.getAmount());
                    glassService.createGlassLog(glass, user, reward.getAmount());
                }
            }
        }

        Experience experience = experienceService.getExperience(9);
        user.increaseExpCnt(experience.getAmount());
        experienceService.createExperienceLog(experience, user);
    }
}
