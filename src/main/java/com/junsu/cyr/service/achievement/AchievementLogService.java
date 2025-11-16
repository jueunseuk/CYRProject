package com.junsu.cyr.service.achievement;

import com.junsu.cyr.domain.achievements.AchievementLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.AchievementLogRepository;
import com.junsu.cyr.response.exception.code.AchievementExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementLogService {

    private final AchievementLogRepository achievementLogRepository;

    public AchievementLog getAchievementLog(Long achievementLogId) {
        return achievementLogRepository.findById(achievementLogId)
                .orElseThrow(() -> new BaseException(AchievementExceptionCode.NOT_FOUND_ACHIEVEMENT_LOG));
    }

    public List<AchievementLog> getAchievementLogs(User user, Pageable pageable) {
        return achievementLogRepository.findAllByUser(user, pageable);
    }
}
