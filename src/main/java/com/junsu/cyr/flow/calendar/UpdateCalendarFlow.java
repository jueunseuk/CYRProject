package com.junsu.cyr.flow.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.calendar.CalendarEditRequest;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
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

    @Transactional
    public void updateCalendar(Long calendarId, CalendarEditRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

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
