package com.junsu.cyr.service.sand;

import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.sand.SandLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.repository.SandLogRepository;
import com.junsu.cyr.repository.SandRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.SandExceptionCode;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SandService {

    private final SandRepository sandRepository;
    private final SandLogRepository sandLogRepository;
    private final UserService userService;

    public Sand getSand(Integer sandId) {
        return sandRepository.findById(sandId)
                .orElseThrow(() -> new BaseException(SandExceptionCode.NOT_FOUND_SAND));
    }

    public void createSandLog(Sand sand, User user) {
        SandLog sandLog = SandLog.builder()
                .sand(sand)
                .user(user)
                .after(user.getSand())
                .build();

        sandLogRepository.save(sandLog);
    }

    public UserAssetDateResponse getAssetData(Integer userId) {
        User user = userService.getUserById(userId);

        UserAssetDateResponse response = new UserAssetDateResponse();
        response.setCurrent(user.getEpxCnt());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayMidnight = LocalDate.now().atStartOfDay();

        Long todayExp = countDuringPeriod(userId, null, todayMidnight, now);
        response.setToday(todayExp);

        LocalDateTime yesterdayMidnight = LocalDate.now().minusDays(1).atStartOfDay();
        Long yesterdayExp = countDuringPeriod(userId, null, yesterdayMidnight, todayMidnight);

        response.setIncrementByDay(todayExp - yesterdayExp);

        LocalDate monday = LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime thisWeekStart = monday.atStartOfDay();
        Long weekExp = countDuringPeriod(userId, null, thisWeekStart, now);
        response.setWeek(weekExp);

        LocalDate lastMonday = monday.minusWeeks(1);
        LocalDateTime lastWeekStart = lastMonday.atStartOfDay();
        Long lastWeekExp = countDuringPeriod(userId, null, lastWeekStart, thisWeekStart);

        response.setIncrementByWeek(weekExp - lastWeekExp);

        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDateTime thisMonthStart = firstDayOfMonth.atStartOfDay();
        Long monthExp = countDuringPeriod(userId, null, thisMonthStart, now);
        response.setMonth(monthExp);

        LocalDate firstDayOfLastMonth = firstDayOfMonth.minusMonths(1);
        LocalDateTime lastMonthStart = firstDayOfLastMonth.atStartOfDay();
        Long lastMonthExp = countDuringPeriod(userId, null, lastMonthStart, thisMonthStart);

        response.setIncrementByMonth(monthExp - lastMonthExp);

        return response;
    }

    private Long countDuringPeriod(Integer userId, Integer sandId, LocalDateTime start, LocalDateTime end) {
        List<SandLog> sandLogs;
        if(userId != null && sandId != null) {
            sandLogs = sandLogRepository.findAllByUserIdAndSandIdAndCreatedAtBetween(userId, sandId, start, end);
        } else if(userId != null) {
            sandLogs = sandLogRepository.findAllByUserIdAndCreatedAtBetween(userId, start, end);
        } else if(sandId != null) {
            sandLogs = sandLogRepository.findAllBySandIdAndCreatedAtBetween(sandId, start, end);
        } else {
            sandLogs = sandLogRepository.findAllByCreatedAtBetween(start, end);
        }

        long count = 0;
        for (SandLog sandLog : sandLogs) {
            count += sandLog.getSand().getAmount();
        }

        return count;
    }
}
