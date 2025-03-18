package com.junsu.cyr.domain.attendances;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendance")
public class Attendance {
    @EmbeddedId
    private AttendanceId attendanceId;

    @Column(name = "comment", nullable = false)
    private String comment;
}
