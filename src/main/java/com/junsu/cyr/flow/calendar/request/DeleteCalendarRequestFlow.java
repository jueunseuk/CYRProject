package com.junsu.cyr.flow.calendar.request;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CalendarRequestRepository;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.calendar.CalendarRequestService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCalendarRequestFlow {

    private final UserService userService;
    private final CalendarRequestService calendarRequestService;
    private final CalendarRequestRepository calendarRequestRepository;

    @Transactional
    public void deleteCalendarRequest(Long calendarRequestId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!user.getUserId().equals(userId)) {
            throw new BaseException(CalendarExceptionCode.NOT_PERSON_THE_PARTY_YOU_REQUESTED);
        }

        CalendarRequest calendarRequest = calendarRequestService.getCalendarRequestByCalendarRequestId(calendarRequestId);
        calendarRequestRepository.delete(calendarRequest);
    }
}
