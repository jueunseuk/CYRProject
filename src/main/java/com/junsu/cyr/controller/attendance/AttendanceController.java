package com.junsu.cyr.controller.attendance;

import com.junsu.cyr.model.attendance.AttendanceListResponse;
import com.junsu.cyr.model.attendance.AttendanceRequest;
import com.junsu.cyr.model.attendance.AttendanceWeekCntResponse;
import com.junsu.cyr.service.attendance.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("")
    public ResponseEntity<?> makeAttendance(@RequestAttribute Integer userId, AttendanceRequest request) {
        attendanceService.makeAttendance(userId, request);
        return ResponseEntity.ok("success to make attendance");
    }

    @GetMapping("/today")
    public ResponseEntity<List<AttendanceListResponse>> getTodayAttendance() {
        List<AttendanceListResponse> attendanceListResponses = attendanceService.getAttendanceList();
        return ResponseEntity.ok(attendanceListResponses);
    }

    @GetMapping("/statistic")
    public ResponseEntity<AttendanceWeekCntResponse> getAttendanceCnt() {
        AttendanceWeekCntResponse attendanceWeekCntResponses = attendanceService.getAttendanceCnt();
        return ResponseEntity.ok(attendanceWeekCntResponses);
    }
}
