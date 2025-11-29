package com.junsu.cyr.service.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.model.calendar.*;
import com.junsu.cyr.repository.CalendarRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public Calendar getCalendarByCalendarId(Long calendarId) {
        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new BaseException(CalendarExceptionCode.NOT_EXIST_CALENDAR_ID));
    }

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

    public List<CalendarResponse> getCalendarBeforeList(Integer before) {
        List<Calendar> list = calendarRepository.findTop5ByOrder(LocalDate.now().minusDays(before), LocalDate.now());
        return list.stream().map(CalendarResponse::new).toList();
    }

    public List<CalendarResponse> getCalendarAfterList(Integer after) {
        List<Calendar> list = calendarRepository.findTop5ByOrder(LocalDate.now(), LocalDate.now().plusDays(after));
        Collections.reverse(list);
        return list.stream().map(CalendarResponse::new).toList();
    }
}
