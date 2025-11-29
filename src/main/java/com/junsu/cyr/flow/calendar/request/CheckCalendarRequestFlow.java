package com.junsu.cyr.flow.calendar.request;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
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

    @Transactional
    public void checkCalendarRequest(Long calendarRequestId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

        CalendarRequest calendarRequest = calendarRequestService.getCalendarRequestByCalendarRequestId(calendarRequestId);
        calendarRequest.updateStatus();
    }
}
