package com.junsu.cyr.flow.calendar;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CalendarRepository;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCalendarFlow {

    private final UserService userService;
    private final CalendarRepository calendarRepository;

    @Transactional
    public void deleteCalendar(Long calendarId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

        calendarRepository.deleteById(calendarId);
    }
}
