package com.junsu.cyr.service.glass;

import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.glass.GlassLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.repository.GlassLogRepository;
import com.junsu.cyr.repository.GlassRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.repository.projection.DailyMaxProjection;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.GlassExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassService {

    private final GlassRepository glassRepository;
    private final GlassLogRepository glassLogRepository;
    private final UserRepository userRepository;

    public Glass getGlass(Integer glassLogId) {
        return glassRepository.findById(glassLogId)
                .orElseThrow(() -> new BaseException(GlassExceptionCode.NOT_FOUND_GLASS));
    }

    public void createGlassLog(Glass glass, User user) {
        GlassLog glassLog = GlassLog.builder()
                .glass(glass)
                .after(user.getGlass())
                .user(user)
                .build();

        glassLogRepository.save(glassLog);
    }

    public UserAssetDataResponse getAssetData(Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        UserAssetDataResponse response = new UserAssetDataResponse();
        response.setCurrent(Long.valueOf(user.getGlass()));

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

    public List<GraphResponse> getGlassHistory(Integer userId) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime aYearAgo = today.minusYears(1);

        List<DailyMaxProjection> rows = glassLogRepository.findDailyMaxByUserId(userId, aYearAgo, today);

        List<GraphResponse.Point> points = rows.stream()
                .map(r -> new GraphResponse.Point(
                        r.getDate(),
                        r.getAfter()
                ))
                .toList();

        return List.of(new GraphResponse("유리 조각", points));
    }

    private Long countDuringPeriod(Integer userId, Integer glassLogId, LocalDateTime start, LocalDateTime end) {
        List<GlassLog> glassLogs;
        if(userId != null && glassLogId != null) {
            glassLogs = glassLogRepository.findAllByUserIdAndGlassIdAndCreatedAtBetween(userId, glassLogId, start, end);
        } else if(userId != null) {
            glassLogs = glassLogRepository.findAllByUserIdAndCreatedAtBetween(userId, start, end);
        } else if(glassLogId != null) {
            glassLogs = glassLogRepository.findAllByGlassIdAndCreatedAtBetween(glassLogId, start, end);
        } else {
            glassLogs = glassLogRepository.findAllByCreatedAtBetween(start, end);
        }

        long count = 0;
        for (GlassLog glassLog : glassLogs) {
            count += glassLog.getGlass().getAmount();
        }

        return count;
    }
}
