package com.junsu.cyr.model.calendar;

import com.junsu.cyr.domain.calendar.Type;
import lombok.Data;

@Data
public class CalendarEditRequest {
    private Long calendarId;
    private String title;
    private String description;
    private String location;
    private String date;
    private Type type;
}
