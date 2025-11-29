package com.junsu.cyr.controller.calendar;

import com.junsu.cyr.flow.calendar.CreateCalendarFlow;
import com.junsu.cyr.flow.calendar.DeleteCalendarFlow;
import com.junsu.cyr.flow.calendar.UpdateCalendarFlow;
import com.junsu.cyr.model.calendar.*;
import com.junsu.cyr.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final CreateCalendarFlow createCalendarFlow;
    private final UpdateCalendarFlow updateCalendarFlow;
    private final DeleteCalendarFlow deleteCalendarFlow;

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyScheduleResponse> getMonthlySchedule(@RequestParam Integer year, @RequestParam Integer month) {
        MonthlyScheduleResponse monthlyScheduleResponse = calendarService.getMonthlySchedule(year, month);
        return ResponseEntity.ok(monthlyScheduleResponse);
    }

    @PostMapping("")
    public ResponseEntity<?> uploadSchedule(@ModelAttribute CalendarUploadRequest request, @RequestAttribute Integer userId) {
        createCalendarFlow.createCalendar(request, userId);
        return ResponseEntity.ok("success to add schedule");
    }

    @PatchMapping("/{calendarId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long calendarId, @ModelAttribute CalendarEditRequest request, @RequestAttribute Integer userId) {
        updateCalendarFlow.updateCalendar(calendarId, request, userId);
        return ResponseEntity.ok("success to update schedule");
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteSchedule(@RequestParam Long calendarId, @RequestAttribute Integer userId) {
        deleteCalendarFlow.deleteCalendar(calendarId, userId);
        return ResponseEntity.ok("success to delete schedule");
    }

    @GetMapping("/before")
    public ResponseEntity<List<CalendarResponse>> getScheduleBefore(@RequestParam Integer before) {
        List<CalendarResponse> calendarResponseList = calendarService.getCalendarBeforeList(before);
        return ResponseEntity.ok(calendarResponseList);
    }

    @GetMapping("/after")
    public ResponseEntity<List<CalendarResponse>> getScheduleAfter(@RequestParam Integer after) {
        List<CalendarResponse> calendarResponseList = calendarService.getCalendarAfterList(after);
        return ResponseEntity.ok(calendarResponseList);
    }
}
