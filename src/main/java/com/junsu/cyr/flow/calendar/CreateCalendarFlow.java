package com.junsu.cyr.flow.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.images.Image;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.calendar.CalendarUploadRequest;
import com.junsu.cyr.repository.CalendarRepository;
import com.junsu.cyr.service.image.ImageService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateCalendarFlow {

    private final UserService userService;
    private final CalendarRepository calendarRepository;
    private final ImageService imageService;

    @ManagerOnly
    @Transactional
    public void createCalendar(CalendarUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Calendar calendar = Calendar.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .date(LocalDate.parse(request.getDate()))
                .type(request.getType())
                .user(user)
                .link1(request.getLink1())
                .link2(request.getLink2())
                .build();

        if(request.getFile() != null) {
            Image image = imageService.uploadImage(request.getFile(), calendar.getCalendarId(), Type.SCHEDULE);
            calendar.updateImageUrl(image.getUrl());
        }

        calendarRepository.save(calendar);
    }
}
