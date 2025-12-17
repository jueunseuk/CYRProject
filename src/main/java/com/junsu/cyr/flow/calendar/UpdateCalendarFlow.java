package com.junsu.cyr.flow.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.calendar.CalendarEditRequest;
import com.junsu.cyr.service.calendar.CalendarService;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCalendarFlow {

    private final UserService userService;
    private final CalendarService calendarService;
    private final S3Service s3Service;

    @ManagerOnly
    @Transactional
    public void updateCalendar(Long calendarId, CalendarEditRequest request, Integer userId) {
        userService.getUserById(userId);

        Calendar calendar = calendarService.getCalendarByCalendarId(calendarId);

        String newImageUrl = null;
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            newImageUrl = s3Service.uploadFile(request.getFile(), Type.SCHEDULE);
            calendar.updateImageUrl(newImageUrl);
        } else if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            calendar.updateImageUrl(request.getImageUrl());
        }

        calendar.updateCalendar(request);
    }
}
