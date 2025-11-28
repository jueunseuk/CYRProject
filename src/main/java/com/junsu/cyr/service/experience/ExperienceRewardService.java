package com.junsu.cyr.service.experience;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExperienceRewardService {

    private final ExperienceService experienceService;

    @Transactional
    public void addExperience(User user, Integer experienceId) {
        Experience experience = experienceService.getExperience(experienceId);
        user.increaseExpCnt(experience.getAmount());
        experienceService.createExperienceLog(experience, user);
    }

    @Transactional
    public void addExperience(User user, Experience experience, Integer amount) {
        user.increaseExpCnt(amount);
        experienceService.createExperienceLog(experience, user);
    }
}
