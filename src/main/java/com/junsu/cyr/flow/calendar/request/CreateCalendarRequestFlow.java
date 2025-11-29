package com.junsu.cyr.flow.calendar.request;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.calendar.CalendarRequestRequest;
import com.junsu.cyr.service.calendar.CalendarRequestService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCalendarRequestFlow {

    private final UserService userService;
    private final CalendarRequestService calendarRequestService;

    @Transactional
    public void createCalendarRequest(CalendarRequestRequest request, Integer userId) {
        User user = userService.getUserById(userId);
        calendarRequestService.createCalendarRequest(user, request.getContent());
    }
}
