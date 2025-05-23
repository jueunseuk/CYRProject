package com.junsu.cyr.controller.calendar;

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

    @GetMapping("/request/monthly")
    public ResponseEntity<MonthlyScheduleResponse> getMonthlySchedule(@RequestParam Integer year, @RequestParam Integer month) {
        MonthlyScheduleResponse monthlyScheduleResponse = calendarService.getMonthlySchedule(year, month);
        return ResponseEntity.ok(monthlyScheduleResponse);
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestSchedule(@RequestBody CalendarRequestRequest request, @RequestAttribute Integer userId) {
        calendarService.uploadScheduleRequest(request.getContent(), userId);
        return ResponseEntity.ok("success to upload request");
    }

    @GetMapping("/request/all")
    public ResponseEntity<List<ScheduleRequestResponse>> requestSchedule() {
        List<ScheduleRequestResponse> scheduleRequestResponse = calendarService.getSchedule();
        return ResponseEntity.ok(scheduleRequestResponse);
    }

    @PatchMapping("/request/process")
    public ResponseEntity<?> processSchedule(@RequestParam Long calendarRequestId, @RequestAttribute Integer userId) {
        calendarService.checkScheduleRequest(calendarRequestId, userId);
        return ResponseEntity.ok("success to process request");
    }

    @PatchMapping("/request")
    public ResponseEntity<?> modifySchedule(@RequestBody CalendarRequestUpdateRequest request, @RequestAttribute Integer userId) {
        calendarService.updateScheduleRequest(request, userId);
        return ResponseEntity.ok("success to modify request");
    }

    @DeleteMapping("/request")
    public ResponseEntity<?> deleteScheduleRequest(@RequestParam Long calendarRequestId, @RequestAttribute Integer userId) {
        calendarService.deleteScheduleRequest(calendarRequestId, userId);
        return ResponseEntity.ok("success to delete request");
    }

    @PostMapping("")
    public ResponseEntity<?> uploadSchedule(@RequestBody CalendarUploadRequest request, @RequestAttribute Integer userId) {
        calendarService.uploadSchedule(request, userId);
        return ResponseEntity.ok("success to add schedule");
    }

    @PutMapping("")
    public ResponseEntity<?> updateSchedule(@RequestBody CalendarUploadRequest request, @RequestAttribute Integer userId) {
        calendarService.updateSchedule(request, userId);
        return ResponseEntity.ok("success to update schedule");
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteSchedule(@RequestParam Long calendarId, @RequestAttribute Integer userId) {
        calendarService.deleteSchedule(calendarId, userId);
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
