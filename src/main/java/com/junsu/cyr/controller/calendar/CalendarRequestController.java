package com.junsu.cyr.controller.calendar;

import com.junsu.cyr.flow.calendar.request.CheckCalendarRequestFlow;
import com.junsu.cyr.flow.calendar.request.CreateCalendarRequestFlow;
import com.junsu.cyr.flow.calendar.request.DeleteCalendarRequestFlow;
import com.junsu.cyr.flow.calendar.request.UpdateCalendarRequestFlow;
import com.junsu.cyr.model.calendar.*;
import com.junsu.cyr.service.calendar.CalendarRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar/request")
public class CalendarRequestController {

    private final CreateCalendarRequestFlow createCalendarRequestFlow;
    private final CalendarRequestService calendarRequestService;
    private final CheckCalendarRequestFlow checkCalendarRequestFlow;
    private final UpdateCalendarRequestFlow updateCalendarRequestFlow;
    private final DeleteCalendarRequestFlow deleteCalendarRequestFlow;

    @PostMapping
    public ResponseEntity<?> requestSchedule(@RequestBody CalendarRequestRequest request, @RequestAttribute Integer userId) {
        createCalendarRequestFlow.createCalendarRequest(request, userId);
        return ResponseEntity.ok("success to upload request");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleRequestResponse>> requestSchedule() {
        List<ScheduleRequestResponse> scheduleRequestResponse = calendarRequestService.getSchedule();
        return ResponseEntity.ok(scheduleRequestResponse);
    }

    @PatchMapping("/process")
    public ResponseEntity<?> processSchedule(@RequestParam Long calendarRequestId, @RequestAttribute Integer userId) {
        checkCalendarRequestFlow.checkCalendarRequest(calendarRequestId, userId);
        return ResponseEntity.ok("success to process request");
    }

    @PatchMapping
    public ResponseEntity<?> modifySchedule(@RequestBody CalendarRequestUpdateRequest request, @RequestAttribute Integer userId) {
        updateCalendarRequestFlow.updateCalendarRequest(request, userId);
        return ResponseEntity.ok("success to modify request");
    }

    @DeleteMapping("/{calendarRequestId}")
    public ResponseEntity<?> deleteScheduleRequest(@PathVariable Long calendarRequestId, @RequestAttribute Integer userId) {
        deleteCalendarRequestFlow.deleteCalendarRequest(calendarRequestId, userId);
        return ResponseEntity.ok("success to delete request");
    }
}
