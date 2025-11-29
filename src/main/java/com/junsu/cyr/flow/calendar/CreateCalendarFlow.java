package com.junsu.cyr.flow.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.calendar.CalendarUploadRequest;
import com.junsu.cyr.repository.CalendarRepository;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateCalendarFlow {

    private final UserService userService;
    private final S3Service s3Service;
    private final CalendarRepository calendarRepository;

    @Transactional
    public void createCalendar(CalendarUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

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
            String imageUrl = s3Service.uploadFile(request.getFile(), Type.SCHEDULE);
            calendar.updateImageUrl(imageUrl);
        }

        calendarRepository.save(calendar);
    }
}
