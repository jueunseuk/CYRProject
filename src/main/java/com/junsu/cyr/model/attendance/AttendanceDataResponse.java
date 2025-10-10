package com.junsu.cyr.model.attendance;

import lombok.Data;

@Data
public class AttendanceDataResponse {
    private Long total;
    private Long consecutiveCnt;
    private Long week;
    private Long incrementByWeek;
    private Long month;
    private Long incrementByMonth;
    private Long year;
    private Long incrementByYear;
}
