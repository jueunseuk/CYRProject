package com.junsu.cyr.controller.calendar;

import com.junsu.cyr.model.calendar.CalendarRequestUpdateRequest;
import com.junsu.cyr.model.calendar.MonthlyScheduleResponse;
import com.junsu.cyr.model.calendar.ScheduleRequestResponse;
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

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyScheduleResponse> getMonthlySchedule(@RequestParam Integer year, @RequestParam Integer month) {
        MonthlyScheduleResponse monthlyScheduleResponse = calendarService.getMonthlySchedule(year, month);
        return ResponseEntity.ok(monthlyScheduleResponse);
    }

    @PostMapping("")
    public ResponseEntity<?> requestSchedule(@RequestBody String content, @RequestAttribute Integer userId) {
        calendarService.uploadSchedule(content, userId);
        return ResponseEntity.ok("success to upload request");
    }

    @GetMapping("/request")
    public ResponseEntity<List<ScheduleRequestResponse>> requestSchedule() {
        List<ScheduleRequestResponse> scheduleRequestResponse = calendarService.getSchedule();
        return ResponseEntity.ok(scheduleRequestResponse);
    }

    @PatchMapping("/process")
    public ResponseEntity<?> processSchedule(@RequestParam Long calendarRequestId, @RequestAttribute Integer userId) {
        calendarService.updateSchedule(calendarRequestId, userId);
        return ResponseEntity.ok("success to process request");
    }

    @PatchMapping("")
    public ResponseEntity<?> modifySchedule(@RequestBody CalendarRequestUpdateRequest request, @RequestAttribute Integer userId) {
        calendarService.updateCalendarRequest(request, userId);
        return ResponseEntity.ok("success to modify request");
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteSchedule(@RequestParam Long calendarRequestId, @RequestAttribute Integer userId) {
        calendarService.deleteCalendarRequest(calendarRequestId, userId);
        return ResponseEntity.ok("success to delete request");
    }

}
