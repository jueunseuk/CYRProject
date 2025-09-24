package com.junsu.cyr.model.calendar;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MonthlyScheduleResponse {
    private Map<Integer, List<CalendarResponse>> schedule;

    public MonthlyScheduleResponse(Map<Integer, List<CalendarResponse>> schedule) {
        this.schedule = schedule;
    }
}
