package com.junsu.cyr.model.attendance;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceListResponse {
    private Integer userId;
    private String userNickname;
    private String profileImageUrl;
    private Integer attendanceCnt;
    private LocalDateTime createdAt;
    private String comment;

    public AttendanceListResponse(User user, Attendance attendance) {
        this.userId = user.getUserId();
        this.userNickname = user.getNickname();
        this.profileImageUrl = user.getProfileUrl();
        this.attendanceCnt = user.getAttendanceCnt();
        this.createdAt = attendance.getCreatedAt();
        this.comment = attendance.getComment();
    }
}
