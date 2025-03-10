package com.junsu.cyr.domain.attendance;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AttendanceId implements Serializable {
    @Column(name = "attendance_created_at", nullable = false, updatable = false)
    private LocalDateTime attendanceCreatedAt;

    @Column(name = "attendance_user_id", nullable = false)
    private Integer userId;
}
