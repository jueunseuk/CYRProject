package com.junsu.cyr.model.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AttendanceHistoryResponse {
    private Long value;
    private LocalDate day;
}
