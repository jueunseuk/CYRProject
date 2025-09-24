package com.junsu.cyr.domain.attendances;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendance")
public class Attendance extends BaseTime {
    @EmbeddedId
    private AttendanceId attendanceId;

    @Column(name = "comment", nullable = false)
    private String comment;

    public Integer getUserId() {
        return attendanceId.getUserId();
    }

    public LocalDate getAttendanceDate() {
        return attendanceId.getAttendedAt();
    }
}
