package com.junsu.cyr.service.experience;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.experiences.ExperienceLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.repository.ExperienceLogRepository;
import com.junsu.cyr.repository.ExperienceRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.repository.projection.DailyMaxProjection;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ExperienceExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceLogRepository experienceLogRepository;
    private final UserRepository userRepository;

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

    public UserAssetDataResponse getAssetData(Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        UserAssetDataResponse response = new UserAssetDataResponse();
        response.setCurrent(user.getEpxCnt());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayMidnight = LocalDate.now().atStartOfDay();

        Long today = countDuringPeriod(userId, null, todayMidnight, now);
        response.setToday(today);

        LocalDateTime yesterdayMidnight = LocalDate.now().minusDays(1).atStartOfDay();
        Long yesterday = countDuringPeriod(userId, null, yesterdayMidnight, todayMidnight);

        response.setIncrementByDay(today - yesterday);

        LocalDate monday = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime thisWeekStart = monday.atStartOfDay();
        Long week = countDuringPeriod(userId, null, thisWeekStart, now);
        response.setWeek(week);

        LocalDate lastMonday = monday.minusWeeks(1);
        LocalDateTime lastWeekStart = lastMonday.atStartOfDay();
        Long lastWeek = countDuringPeriod(userId, null, lastWeekStart, thisWeekStart);

        response.setIncrementByWeek(week - lastWeek);

        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDateTime thisMonthStart = firstDayOfMonth.atStartOfDay();
        Long month = countDuringPeriod(userId, null, thisMonthStart, now);
        response.setMonth(month);

        LocalDate firstDayOfLastMonth = firstDayOfMonth.minusMonths(1);
        LocalDateTime lastMonthStart = firstDayOfLastMonth.atStartOfDay();
        Long lastMonth = countDuringPeriod(userId, null, lastMonthStart, thisMonthStart);

        response.setIncrementByMonth(month - lastMonth);

        return response;
    }

    public List<GraphResponse> getExperienceHistory(Integer userId) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime aYearAgo = today.minusYears(1);

        List<DailyMaxProjection> rows = experienceLogRepository.findDailyMaxByUserId(userId, aYearAgo, today);

        List<GraphResponse.Point> points = rows.stream()
                .map(r -> new GraphResponse.Point(
                        r.getDate(),
                        r.getAfter()
                ))
                .toList();

        return List.of(new GraphResponse("경험치", points));
    }

    private Long countDuringPeriod(Integer userId, Integer experienceId, LocalDateTime start, LocalDateTime end) {
        List<ExperienceLog> experienceLogs;
        if(userId != null && experienceId != null) {
            experienceLogs = experienceLogRepository.findAllByUserIdAndExperienceIdAndCreatedAtBetween(userId, experienceId, start, end);
        } else if(userId != null) {
            experienceLogs = experienceLogRepository.findAllByUserIdAndCreatedAtBetween(userId, start, end);
        } else if(experienceId != null) {
            experienceLogs = experienceLogRepository.findAllByExperienceIdAndCreatedAtBetween(experienceId, start, end);
        } else {
            experienceLogs = experienceLogRepository.findAllByCreatedAtBetween(start, end);
        }

        long count = 0;
        for (ExperienceLog experienceLog : experienceLogs) {
            count += experienceLog.getExperience().getAmount();
        }

        return count;
    }
}
