package com.junsu.cyr.flow.calendar;

import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.repository.CalendarRepository;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCalendarFlow {

    private final UserService userService;
    private final CalendarRepository calendarRepository;

    @ManagerOnly
    @Transactional
    public void deleteCalendar(Long calendarId, Integer userId) {
        userService.getUserById(userId);

        calendarRepository.deleteById(calendarId);
    }
}
