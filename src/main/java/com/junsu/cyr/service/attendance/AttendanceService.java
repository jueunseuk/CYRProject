package com.junsu.cyr.service.attendance;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.attendance.*;
import com.junsu.cyr.repository.AttendanceRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.AttendanceExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.notification.usecase.TemperatureNotificationUseCase;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TemperatureNotificationUseCase temperatureNotificationUseCase;

    @Transactional
    public void makeAttendance(User user, AttendanceRequest request) {
        AttendanceId attendanceId = new AttendanceId(user.getUserId(), LocalDate.now());

        Optional<Attendance> attendance = attendanceRepository.findByAttendanceId(attendanceId);
        if(attendance.isPresent()) {
            throw new BaseException(AttendanceExceptionCode.ALREADY_ATTEND_USER);
        }

        userService.addExpAndSand(user, 4, 12);

        long gap = getGapWithLastAttendanceDate(user);
        if(gap == 0) {
            userService.addTemperature(user, 1);
            user.initConsecutiveAttendanceCnt();
        } else if(gap == 1) {
            userService.addTemperature(user, 2);
            user.increaseConsecutiveAttendanceCnt();
        } else if(gap >= 2) {
            user.initConsecutiveAttendanceCnt();
            if(gap <= 3) {
                userService.addTemperature(user, 7);
            } else if(gap <= 30) {
                userService.addTemperature(user, 8);
            } else {
                user.initTemperature();
            }
        }

        if(user.getTemperature() == 1800) {
            if(user.getSand() < 100) {
                temperatureNotificationUseCase.temperatureMaximumReached(user);
            } else {
                temperatureNotificationUseCase.temperatureMaximumReachAndCraftGlass(user);
            }
        }

        if(user.getConsecutiveAttendanceCnt() % 7 == 0) {
            userService.addTemperature(user, 3);
        } else if(user.getConsecutiveAttendanceCnt() % 30 == 0) {
            userService.addTemperature(user, 4);
        } else if(user.getConsecutiveAttendanceCnt() % 100 == 0) {
            userService.addTemperature(user, 5);
        } else if(user.getConsecutiveAttendanceCnt() % 365 == 0) {
            userService.addTemperature(user, 6);
        }

        createAttendance(attendanceId, request);
        user.updateAttendanceCnt();
        userRepository.save(user);
    }

    private long getGapWithLastAttendanceDate(User user) {
        Optional<Attendance> getAttendance = getLastAttendanceDate(user.getUserId());
        if(getAttendance.isEmpty()) {
            return 0;
        }

        Attendance lastAttendance = getAttendance.get();
        LocalDate lastDate = lastAttendance.getCreatedAt().toLocalDate();
        LocalDate today = LocalDate.now();

        return ChronoUnit.DAYS.between(lastDate, today);
    }

    public Optional<Attendance> getLastAttendanceDate(Integer userId) {
        return attendanceRepository.findTopByAttendanceIdUserIdOrderByCreatedAtDesc(userId);
    }

    public void createAttendance(AttendanceId attendanceId, AttendanceRequest request) {
        if(request.getComment() == null || request.getComment().isEmpty()) {
            throw new BaseException(AttendanceExceptionCode.CONTENT_DOES_NOT_EXISTS);
        }

        Attendance attendance = Attendance.builder()
                .attendanceId(attendanceId)
                .comment(request.getComment())
                .build();

        attendanceRepository.save(attendance);
    }

    public List<AttendanceListResponse> getAttendanceList() {
        LocalDate today = LocalDate.now();

        List<Attendance> attendanceList = attendanceRepository.findTodayAttendance(today);

        return attendanceList.stream()
                .map(att -> {
                    Integer userId = att.getAttendanceId().getUserId();
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

                    return new AttendanceListResponse(user, att);
                })
                .toList();
    }

    public AttendanceWeekCntResponse getAttendanceCnt() {
        LocalDate now = LocalDate.now();

        LocalDate thisWeekStart = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate thisWeekEnd   = thisWeekStart.plusDays(6);

        LocalDate thisMonthStart = now.withDayOfMonth(1);
        LocalDate thisMonthEnd = now.withDayOfMonth(now.lengthOfMonth());

        LocalDate lastMonthStart = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastMonthEnd = lastMonthStart.withDayOfMonth(lastMonthStart.lengthOfMonth());

        LocalDate lastWeekStart = thisWeekStart.minusWeeks(1);
        LocalDate lastWeekEnd = thisWeekStart.minusDays(1);

        Integer thisMonth = attendanceRepository.findAttendanceCnt(thisMonthStart, thisMonthEnd);
        Integer lastMonth = attendanceRepository.findAttendanceCnt(lastMonthStart, lastMonthEnd);
        Integer thisWeek = attendanceRepository.findAttendanceCnt(thisWeekStart, thisWeekEnd);
        Integer lastWeek = attendanceRepository.findAttendanceCnt(lastWeekStart, lastWeekEnd);

        List<AttendanceDailyCount> weeksCnt = attendanceRepository.findWeeklyAttendanceByDay(thisWeekStart, thisWeekEnd);

        Map<String, Integer> listByDay = convertToWeekMap(weeksCnt);

        return new AttendanceWeekCntResponse(thisMonth, lastMonth, thisWeek, lastWeek, listByDay);
    }

    public Map<String, Integer> convertToWeekMap(List<AttendanceDailyCount> weekCounts) {
        Map<String, Integer> weekMap = new LinkedHashMap<>();

        weekMap.put("sunday", 0);
        weekMap.put("monday", 0);
        weekMap.put("tuesday", 0);
        weekMap.put("wednesday", 0);
        weekMap.put("thursday", 0);
        weekMap.put("friday", 0);
        weekMap.put("saturday", 0);

        for (AttendanceDailyCount count : weekCounts) {
            DayOfWeek dayOfWeek = count.getDate().getDayOfWeek();
            String key = dayOfWeek.name().toLowerCase();
            weekMap.put(key, count.getCount());
        }

        return weekMap;
    }

    public AttendanceDataResponse getAssetData(Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        AttendanceDataResponse response = new AttendanceDataResponse();
        response.setTotal(Long.valueOf(user.getAttendanceCnt()));
        response.setConsecutiveCnt(Long.valueOf(user.getConsecutiveAttendanceCnt()));
        response.setMaxConsecutiveCnt(Long.valueOf(user.getMaxConsecutiveAttendanceCnt()));

        LocalDate today = LocalDate.now();

        LocalDate monday = today.with(java.time.DayOfWeek.MONDAY);
        Long thisWeekCount = countDuringPeriod(userId, monday, today);
        response.setWeek(thisWeekCount);

        LocalDate lastMonday = monday.minusWeeks(1);
        LocalDate lastSunday = monday.minusDays(1);
        Long lastWeekCount = countDuringPeriod(userId, lastMonday, lastSunday);
        response.setIncrementByWeek(thisWeekCount - lastWeekCount);

        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        Long thisMonthCount = countDuringPeriod(userId, firstDayOfMonth, today);
        response.setMonth(thisMonthCount);

        LocalDate firstDayOfLastMonth = firstDayOfMonth.minusMonths(1);
        LocalDate lastDayOfLastMonth = firstDayOfMonth.minusDays(1);
        Long lastMonthCount = countDuringPeriod(userId, firstDayOfLastMonth, lastDayOfLastMonth);
        response.setIncrementByMonth(thisMonthCount - lastMonthCount);

        LocalDate firstDayOfYear = today.withDayOfYear(1);
        Long thisYearCount = countDuringPeriod(userId, firstDayOfYear, today);
        response.setYear(thisYearCount);

        LocalDate firstDayOfLastYear = firstDayOfYear.minusYears(1);
        LocalDate lastDayOfLastYear = firstDayOfYear.minusDays(1);
        Long lastYearCount = countDuringPeriod(userId, firstDayOfLastYear, lastDayOfLastYear);
        response.setIncrementByYear(thisYearCount - lastYearCount);

        return response;
    }

    private Long countDuringPeriod(Integer userId, LocalDate start, LocalDate end) {
        return attendanceRepository.countAllByAttendanceId_UserIdAndAttendanceId_AttendedAtBetween(userId, start, end);
    }

    public List<AttendanceHistoryResponse> getAttendanceHistory(Integer userId) {
        LocalDate today = LocalDate.now();
        LocalDate aYearAgo = today.minusYears(1);

        List<Attendance> attendances = attendanceRepository.findAllByAttendanceId_UserIdAndAttendanceId_AttendedAtBetween(userId, aYearAgo, today);

        return attendances.stream()
                .map(attend -> new AttendanceHistoryResponse(1L, attend.getAttendanceDate()))
                .toList();
    }

    @Transactional
    public Integer increaseConsecutiveAttendanceToForce(User user) {
        AttendanceId attendanceId = new AttendanceId(user.getUserId(), LocalDate.now());

        Optional<Attendance> attendance = attendanceRepository.findByAttendanceId(attendanceId);
        if(attendance.isEmpty()) {
            throw new BaseException(AttendanceExceptionCode.NOT_YET_IN_ATTENDANCE);
        }

        user.increaseConsecutiveAttendanceCnt();

        return user.getMaxConsecutiveAttendanceCnt();
    }
}
