package com.junsu.cyr.domain.attendances;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceId extends BaseTime implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Integer userId;
}
