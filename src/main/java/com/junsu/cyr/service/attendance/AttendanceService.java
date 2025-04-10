package com.junsu.cyr.service.attendance;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.attendance.AttendanceDailyCount;
import com.junsu.cyr.model.attendance.AttendanceListResponse;
import com.junsu.cyr.model.attendance.AttendanceRequest;
import com.junsu.cyr.model.attendance.AttendanceWeekCntResponse;
import com.junsu.cyr.repository.AttendanceRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.AttendanceExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public void makeAttendance(Integer userId, AttendanceRequest request) {
        AttendanceId attendanceId = new AttendanceId(userId, LocalDate.now());

        Optional<Attendance> attendance = attendanceRepository.findByAttendanceId(attendanceId);

        if(attendance.isPresent()) {
            throw new BaseException(AttendanceExceptionCode.ALREADY_ATTEND_USER);
        }

        createAttendance(attendanceId, request);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        user.updateAttendanceCnt();
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

}
