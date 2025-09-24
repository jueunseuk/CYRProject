package com.junsu.cyr.model.calendar;

import lombok.Data;

@Data
public class CalendarRequestUpdateRequest {
    private Long calendarRequestId;
    private String content;
}
