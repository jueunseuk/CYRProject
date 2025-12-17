package com.junsu.cyr.flow.calendar.request;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.service.calendar.CalendarRequestService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckCalendarRequestFlow {

    private final UserService userService;
    private final CalendarRequestService calendarRequestService;

    @ManagerOnly
    @Transactional
    public void checkCalendarRequest(Long calendarRequestId, Integer userId) {
        userService.getUserById(userId);

        CalendarRequest calendarRequest = calendarRequestService.getCalendarRequestByCalendarRequestId(calendarRequestId);
        calendarRequest.updateStatus();
    }
}
