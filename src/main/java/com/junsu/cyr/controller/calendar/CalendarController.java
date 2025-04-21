package com.junsu.cyr.controller.calendar;

import com.junsu.cyr.model.calendar.MonthlyScheduleResponse;
import com.junsu.cyr.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyScheduleResponse> getMonthlySchedule(@RequestParam Integer year, @RequestParam Integer month) {
        MonthlyScheduleResponse monthlyScheduleResponse = calendarService.getMonthlySchedule(year, month);
        return ResponseEntity.ok(monthlyScheduleResponse);
    }

}
