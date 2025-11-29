package com.junsu.cyr.flow.calendar.request;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.calendar.CalendarRequestUpdateRequest;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.calendar.CalendarRequestService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCalendarRequestFlow {

    private final UserService userService;
    private final CalendarRequestService calendarRequestService;

    @Transactional
    public void updateCalendarRequest(CalendarRequestUpdateRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!user.getUserId().equals(userId)) {
            throw new BaseException(CalendarExceptionCode.NOT_PERSON_THE_PARTY_YOU_REQUESTED);
        }

        CalendarRequest calendarRequest = calendarRequestService.getCalendarRequestByCalendarRequestId(request.getCalendarRequestId());
        calendarRequest.updateContent(request.getContent());
    }
}
