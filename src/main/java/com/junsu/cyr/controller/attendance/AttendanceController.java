package com.junsu.cyr.controller.attendance;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.attendance.*;
import com.junsu.cyr.service.attendance.AttendanceService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> makeAttendance(@RequestAttribute Integer userId, @RequestBody AttendanceRequest request) {
        User user = userService.getUserById(userId);
        attendanceService.makeAttendance(user, request);
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

    @GetMapping("/data/{userId}")
    public ResponseEntity<AttendanceDataResponse> getAttendanceData(@PathVariable Integer userId) {
        AttendanceDataResponse attendanceDataResponse = attendanceService.getAssetData(userId);
        return ResponseEntity.ok(attendanceDataResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<AttendanceHistoryResponse>> getAttendanceHistory(@PathVariable Integer userId) {
        List<AttendanceHistoryResponse> attendanceHistoryResponses = attendanceService.getAttendanceHistory(userId);
        return ResponseEntity.ok(attendanceHistoryResponses);
    }
}
