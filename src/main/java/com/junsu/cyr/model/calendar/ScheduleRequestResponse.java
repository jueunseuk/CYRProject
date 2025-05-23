package com.junsu.cyr.model.calendar;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

@Data
public class ScheduleRequestResponse {
    private Long calendarRequestId;
    private Integer userId;
    private String userNickname;
    private String profileImageUrl;
    private String content;
    private Boolean status;

    public ScheduleRequestResponse(User user, CalendarRequest calendarRequest) {
        this.calendarRequestId = calendarRequest.getCalendarRequestId();
        this.userId = user.getUserId();
        this.userNickname = user.getNickname();
        this.profileImageUrl = user.getProfileUrl();
        this.content = calendarRequest.getContent();
        this.status = calendarRequest.getStatus();
    }
}
