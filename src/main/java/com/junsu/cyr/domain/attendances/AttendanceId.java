package com.junsu.cyr.domain.attendances;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "attended_at")
    private LocalDate attendedAt;
}
