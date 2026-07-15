package com.junsu.cyr.flow.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.images.Image;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.calendar.CalendarEditRequest;
import com.junsu.cyr.service.calendar.CalendarService;
import com.junsu.cyr.service.image.ImageService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCalendarFlow {

    private final UserService userService;
    private final CalendarService calendarService;
    private final ImageService imageService;

    @ManagerOnly
    @Transactional
    public void updateCalendar(Long calendarId, CalendarEditRequest request, Integer userId) {
        userService.getUserById(userId);

        Calendar calendar = calendarService.getCalendarByCalendarId(calendarId);

        Image image = null;
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            image = imageService.uploadImage(request.getFile(), calendarId, Type.SCHEDULE);
            calendar.updateImageUrl(image.getUrl());
        } else if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            calendar.updateImageUrl(request.getImageUrl());
        }

        calendar.updateCalendar(request);
    }
}
