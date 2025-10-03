package com.junsu.cyr.service.temperature;

import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.temperature.TemperatureLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.repository.TemperatureLogRepository;
import com.junsu.cyr.repository.TemperatureRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.repository.projection.DailyMaxProjection;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.TemperatureExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final TemperatureLogRepository temperatureLogRepository;
    private final UserRepository userRepository;

    public Temperature getTemperature(Integer temperatureId) {
        return temperatureRepository.findById(temperatureId)
                .orElseThrow(() -> new BaseException(TemperatureExceptionCode.NOT_FOUND_TEMPERATURE));
    }

    public void createTemperatureLog(User user, Temperature temperature) {
        TemperatureLog temperatureLog = TemperatureLog.builder()
                .user(user)
                .temperature(temperature)
                .after(user.getTemperature())
                .build();

        temperatureLogRepository.save(temperatureLog);
    }

    public UserAssetDateResponse getAssetData(Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        UserAssetDateResponse response = new UserAssetDateResponse();
        response.setCurrent(Long.valueOf(user.getTemperature()));

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

    public List<GraphResponse> getTemperatureHistory(Integer userId) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime aYearAgo = today.minusYears(1);

        List<DailyMaxProjection> rows = temperatureLogRepository.findDailyMaxByUserId(userId, aYearAgo, today);

        List<GraphResponse.Point> points = rows.stream()
                .map(r -> new GraphResponse.Point(
                        r.getDate(),
                        r.getAfter()
                ))
                .toList();

        return List.of(new GraphResponse("활동 온도", points));
    }

    private Long countDuringPeriod(Integer userId, Integer temperatureId, LocalDateTime start, LocalDateTime end) {
        List<TemperatureLog> temperatureLogs;
        if(userId != null && temperatureId != null) {
            temperatureLogs = temperatureLogRepository.findAllByUserIdAndTemperatureIdAndCreatedAtBetween(userId, temperatureId, start, end);
        } else if(userId != null) {
            temperatureLogs = temperatureLogRepository.findAllByUserIdAndCreatedAtBetween(userId, start, end);
        } else if(temperatureId != null) {
            temperatureLogs = temperatureLogRepository.findAllByTemperatureIdAndCreatedAtBetween(temperatureId, start, end);
        } else {
            temperatureLogs = temperatureLogRepository.findAllByCreatedAtBetween(start, end);
        }

        long count = 0;
        for (TemperatureLog temperatureLog : temperatureLogs) {
            count += temperatureLog.getTemperature().getAmount();
        }

        return count;
    }
}
