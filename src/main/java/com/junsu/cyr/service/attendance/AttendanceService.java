package com.junsu.cyr.service.attendance;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.domain.users.User;
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
import java.time.LocalDateTime;
import java.util.List;
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
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        List<Attendance> attendanceList = attendanceRepository.findTodayAttendance(start, end);

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
        LocalDate thisWeekStart = now.with(DayOfWeek.SUNDAY);

        LocalDate thisMonthStart = now.withDayOfMonth(1);
        LocalDate thisMonthEnd = now.withDayOfMonth(now.lengthOfMonth());

        LocalDate lastMonthStart = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastMonthEnd = lastMonthStart.withDayOfMonth(lastMonthStart.lengthOfMonth());

        LocalDate lastWeekStart = thisWeekStart.minusWeeks(1);
        LocalDate lastWeekEnd = thisWeekStart.minusDays(1);

        Integer thisMonth = attendanceRepository.findMonthlyAttendance(thisMonthStart, thisMonthEnd);
        Integer lastMonth = attendanceRepository.findMonthlyAttendance(lastMonthStart, lastMonthEnd);
        Integer lastWeek = attendanceRepository.findAttendanceCntBetween(lastWeekStart, lastWeekEnd);

        List<Integer> weeksCnt = attendanceRepository.findWeeklyAttendanceByDay(thisWeekStart, now);

        Integer thisWeek = sumThisWeek(weeksCnt);

        return new AttendanceWeekCntResponse(thisMonth, lastMonth, thisWeek, lastWeek, weeksCnt);
    }

    public Integer sumThisWeek(List<Integer> list) {
        Integer sum = 0;
        for(Integer out : list) {
            sum += out;
        }

        return sum;
    }

}
