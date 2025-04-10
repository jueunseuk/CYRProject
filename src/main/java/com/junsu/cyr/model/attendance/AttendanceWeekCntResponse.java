package com.junsu.cyr.model.attendance;

import lombok.Data;

import java.util.Map;

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

    public AttendanceWeekCntResponse(Integer thisMonthCnt, Integer beforeMonthCnt, Integer thisWeekCnt, Integer beforeWeekCnt, Map<String, Integer> list) {
        this.thisMonthCnt = thisMonthCnt;
        this.beforeMonthCnt = beforeMonthCnt;
        this.thisWeekCnt = thisWeekCnt;
        this.beforeWeekCnt = beforeWeekCnt;
        this.sunday = list.get("sunday");
        this.monday = list.get("monday");
        this.tuesday = list.get("tuesday");
        this.wednesday = list.get("wednesday");
        this.thursday = list.get("thursday");
        this.friday = list.get("friday");
        this.saturday = list.get("saturday");
    }
}
