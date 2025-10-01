package com.junsu.cyr.service.experience;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.experiences.ExperienceLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.ExperienceLogRepository;
import com.junsu.cyr.repository.ExperienceRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ExperienceExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceLogRepository experienceLogRepository;

    public Experience getExperience(Integer experienceId) {
        return experienceRepository.findById(experienceId)
                .orElseThrow(() -> new BaseException(ExperienceExceptionCode.NOT_FOUND_EXPERIENCE));
    }

    public void createExperienceLog(Experience experience, User user) {
        ExperienceLog experienceLog = ExperienceLog.builder()
                .experience(experience)
                .after(user.getEpxCnt())
                .user(user)
                .build();

        experienceLogRepository.save(experienceLog);
    }
}
