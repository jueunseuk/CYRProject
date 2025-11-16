package com.junsu.cyr.service.achievement;

import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.repository.AchievementRepository;
import com.junsu.cyr.response.exception.code.AchievementExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;

    public Achievement getAchievement(Integer achievementId) {
        return achievementRepository.findById(achievementId)
                .orElseThrow(() -> new BaseException(AchievementExceptionCode.NOT_FOUND_ACHIEVEMENT));
    }

    public Achievement getAchievement(Type type, Integer conditionAmount) {
        return achievementRepository.findByTypeAndConditionAmount(type, conditionAmount)
                .orElse(null);
    }
}
