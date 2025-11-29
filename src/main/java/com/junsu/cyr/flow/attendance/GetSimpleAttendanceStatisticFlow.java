package com.junsu.cyr.flow.attendance;

import com.junsu.cyr.model.attendance.AttendanceDailyCount;
import com.junsu.cyr.model.attendance.AttendanceWeekCntResponse;
import com.junsu.cyr.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetSimpleAttendanceStatisticFlow {

    private final AttendanceRepository attendanceRepository;

    public AttendanceWeekCntResponse getSimpleAttendanceStatistic() {
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

    private Map<String, Integer> convertToWeekMap(List<AttendanceDailyCount> weekCounts) {
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
