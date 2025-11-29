package com.junsu.cyr.service.attendance;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.attendance.*;
import com.junsu.cyr.repository.AttendanceRepository;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;

    public void createAttendance(AttendanceId attendanceId, String comment) {
        Attendance attendance = Attendance.builder()
                .attendanceId(attendanceId)
                .comment(comment)
                .build();

        attendanceRepository.save(attendance);
    }

    public List<AttendanceListResponse> getAttendanceList() {
        LocalDate today = LocalDate.now();

        List<Attendance> attendanceList = attendanceRepository.findTodayAttendance(today);

        return attendanceList.stream()
                .map(att -> {
                    Integer userId = att.getAttendanceId().getUserId();
                    User user = userService.getUserById(userId);

                    return new AttendanceListResponse(user, att);
                })
                .toList();
    }

    public AttendanceDataResponse getAssetData(Integer userId) {
        User user = userService.getUserById(userId);

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
}
