package com.junsu.cyr.service.calendar;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.calendar.ScheduleRequestResponse;
import com.junsu.cyr.repository.CalendarRequestRepository;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.code.CalendarRequestExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarRequestService {

    private final CalendarRequestRepository calendarRequestRepository;

    public CalendarRequest getCalendarRequestByCalendarRequestId(Long calendarRequestId) {
        return calendarRequestRepository.findCalendarRequestById(calendarRequestId)
                .orElseThrow(() -> new BaseException(CalendarExceptionCode.NOT_EXIST_CALENDAR_REQUEST_ID));
    }

    @Transactional
    public void createCalendarRequest(User user, String content) {
        if(content == null || content.length() < 5) {
            throw new BaseException(CalendarRequestExceptionCode.TOO_SHORT_CONTENT);
        }

        CalendarRequest calendarRequest = CalendarRequest.builder()
                .user(user)
                .content(content)
                .status(false)
                .build();

        calendarRequestRepository.save(calendarRequest);
    }

    public List<ScheduleRequestResponse> getSchedule() {
        return calendarRequestRepository.findScheduleRequestWithBefore()
                .stream()
                .map(cr -> new ScheduleRequestResponse(cr.getUser(), cr))
                .toList();
    }
}
