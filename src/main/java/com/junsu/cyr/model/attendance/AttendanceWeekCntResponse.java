package com.junsu.cyr.model.attendance;

import lombok.Data;

import java.util.List;

@Data
public class AttendanceWeekCntResponse {
    private Integer thisMonthCnt;
    private Integer beforeMonthCnt;
    private Integer thisWeekCnt;
    private Integer beforeWeekCnt;
    private Integer sunday;
    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;

    public AttendanceWeekCntResponse(Integer thisMonthCnt, Integer beforeMonthCnt, Integer thisWeekCnt, Integer beforeWeekCnt, List<Integer> cnt) {
        this.thisMonthCnt = thisMonthCnt;
        this.beforeMonthCnt = beforeMonthCnt;
        this.thisWeekCnt = thisWeekCnt;
        this.beforeWeekCnt = beforeWeekCnt;
        this.sunday = cnt.get(0);
        this.monday = cnt.get(1);
        this.tuesday = cnt.get(2);
        this.wednesday = cnt.get(3);
        this.thursday = cnt.get(4);
        this.friday = cnt.get(5);
        this.saturday = cnt.get(6);
    }
}
