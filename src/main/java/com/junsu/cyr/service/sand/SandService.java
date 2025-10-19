package com.junsu.cyr.service.sand;

import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.sand.SandLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserInventory;
import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.repository.SandLogRepository;
import com.junsu.cyr.repository.SandRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.repository.projection.DailyMaxProjection;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.SandExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.code.UserInventoryExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SandService {

    private final SandRepository sandRepository;
    private final SandLogRepository sandLogRepository;
    private final UserRepository userRepository;

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

    public UserAssetDataResponse getAssetData(Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        UserAssetDataResponse response = new UserAssetDataResponse();
        response.setCurrent(Long.valueOf(user.getSand()));

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

    public List<GraphResponse> getSandHistory(Integer userId) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime aYearAgo = today.minusYears(1);

        List<DailyMaxProjection> rows = sandLogRepository.findDailyMaxByUserId(userId, aYearAgo, today);

        List<GraphResponse.Point> points = rows.stream()
                .map(r -> new GraphResponse.Point(
                        r.getDate(),
                        r.getAfter()
                ))
                .toList();

        return List.of(new GraphResponse("모래알", points));
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

    @Transactional
    public Integer openRandomSandBox(User user) {
        Integer randomCnt = (int) (Math.random() * 250) + 50;

        user.updateSand(randomCnt);

        Sand sand = getSand(15);
        createSandLog(sand, user);

        return randomCnt;
    }
}
