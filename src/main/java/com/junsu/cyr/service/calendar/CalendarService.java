package com.junsu.cyr.service.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.model.calendar.CalendarResponse;
import com.junsu.cyr.model.calendar.MonthlyScheduleResponse;
import com.junsu.cyr.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public MonthlyScheduleResponse getMonthlySchedule(Integer year, Integer month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end   = start.withDayOfMonth(start.lengthOfMonth());

        List<Calendar> monthSchedules = calendarRepository.findByDateBetween(start, end);

        Map<Integer, List<CalendarResponse>> map =
                monthSchedules.stream()
                        .map(CalendarResponse::new)
                        .collect(Collectors.groupingBy(
                                dto -> dto.getDate().getDayOfMonth(),
                                TreeMap::new,
                                Collectors.toList()));

        return new MonthlyScheduleResponse(map);
    }

}
