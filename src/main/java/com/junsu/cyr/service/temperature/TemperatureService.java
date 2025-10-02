package com.junsu.cyr.service.temperature;

import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.temperature.TemperatureLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.repository.TemperatureLogRepository;
import com.junsu.cyr.repository.TemperatureRepository;
import com.junsu.cyr.repository.UserRepository;
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
