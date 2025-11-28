package com.junsu.cyr.flow.cheer;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.cheers.CheerSummaryId;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.repository.CheerSummaryRepository;
import com.junsu.cyr.response.exception.code.CheerSummaryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.cheer.CheerLogService;
import com.junsu.cyr.service.cheer.CheerSummaryService;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MakeCheerFlow {

    private final UserService userService;
    private final CheerLogService cheerLogService;
    private final ExperienceRewardService experienceRewardService;
    private final SandRewardService sandRewardService;
    private final UnlockAchievementFlow unlockAchievementFlow;
    private final CheerSummaryService cheerSummaryService;
    private final CheerSummaryRepository cheerSummaryRepository;

    @Transactional
    public void makeCheer(Integer userId) {
        User user = userService.getUserById(userId);

        CheerSummaryId cheerSummaryId = new CheerSummaryId(user.getUserId(), LocalDate.now());
        CheerSummary cheerSummary = cheerSummaryRepository.findByCheerSummaryId(cheerSummaryId)
                .orElseGet(() -> cheerSummaryService.createCheerSummary(user));

        if (cheerSummary.getUpdatedAt() != null && cheerSummary.getUpdatedAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
            throw new BaseException(CheerSummaryExceptionCode.INVALID_REQUEST_PERIOD);
        }

        user.increaseCheerCnt();

        experienceRewardService.addExperience(user, 5);
        sandRewardService.addSand(user, 13);

        cheerLogService.createCheerLog(user);
        cheerSummary.increase();

        unlockAchievementFlow.unlockAchievement(user, Type.CHEER, Scope.TOTAL, user.getCheerCnt());
        unlockAchievementFlow.unlockAchievement(user, Type.CHEER, Scope.DAILY, cheerSummary.getCount());
    }
}
