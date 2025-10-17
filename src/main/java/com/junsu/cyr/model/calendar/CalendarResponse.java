package com.junsu.cyr.model.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.calendar.Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CalendarResponse {
    private Long calendarId;
    private Type type;
    private String title;
    private String description;
    private String link1;
    private String link2;
    private String imageUrl;
    private String location;
    private LocalDate date;

    public CalendarResponse(Calendar calendar) {
        this.calendarId = calendar.getCalendarId();
        this.type = calendar.getType();
        this.title = calendar.getTitle();
        this.description = calendar.getDescription();
        this.link1 = calendar.getLink1();
        this.link2 = calendar.getLink2();
        this.imageUrl = calendar.getImageUrl();
        this.location = calendar.getLocation();
        this.date = calendar.getDate();
    }
}
