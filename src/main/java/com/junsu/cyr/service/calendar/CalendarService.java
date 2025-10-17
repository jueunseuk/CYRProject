package com.junsu.cyr.service.calendar;

import com.junsu.cyr.domain.calendar.Calendar;
import com.junsu.cyr.domain.calendar.CalendarRequest;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.calendar.*;
import com.junsu.cyr.repository.CalendarRepository;
import com.junsu.cyr.repository.CalendarRequestRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.CalendarExceptionCode;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final UserService userService;
    private final CalendarRepository calendarRepository;
    private final CalendarRequestRepository calendarRequestRepository;
    private final S3Service s3Service;

    public MonthlyScheduleResponse getMonthlySchedule(Integer year, Integer month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end   = start.withDayOfMonth(start.lengthOfMonth());

        List<Calendar> monthSchedules = calendarRepository.findByDateBetween(start, end);

        Map<Integer, List<CalendarResponse>> map =
                monthSchedules.stream()
                        .map(CalendarResponse::new)
                        .collect(Collectors.groupingBy(
                                dto -> dto.getDate().getDayOfMonth(),
                                TreeMap::new,
                                Collectors.toList()));

        return new MonthlyScheduleResponse(map);
    }

    @Transactional
    public void uploadScheduleRequest(String content, Integer userId) {
        User user = userService.getUserById(userId);

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

    @Transactional
    public void checkScheduleRequest(Long calendarRequestId, Integer userId) {
        User user = userService.getUserById(userId);

        if(user.getRole() == Role.GUEST || user.getRole() == Role.MEMBER) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

        CalendarRequest calendarRequest = getCalendarRequest(calendarRequestId);
        calendarRequest.updateStatus();
        calendarRequestRepository.save(calendarRequest);
    }

    @Transactional
    public void updateScheduleRequest(CalendarRequestUpdateRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!user.getUserId().equals(userId)) {
            throw new BaseException(CalendarExceptionCode.NOT_PERSON_THE_PARTY_YOU_REQUESTED);
        }

        CalendarRequest calendarRequest = getCalendarRequest(request.getCalendarRequestId());
        calendarRequest.updateContent(request.getContent());
        calendarRequestRepository.save(calendarRequest);
    }

    public CalendarRequest getCalendarRequest(Long calendarRequestId) {
        return calendarRequestRepository.findCalendarRequestById(calendarRequestId)
                .orElseThrow(() -> new BaseException(CalendarExceptionCode.NOT_EXIST_CALENDAR_REQUEST_ID));
    }

    @Transactional
    public void deleteScheduleRequest(Long calendarRequestId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!user.getUserId().equals(userId)) {
            throw new BaseException(CalendarExceptionCode.NOT_PERSON_THE_PARTY_YOU_REQUESTED);
        }

        CalendarRequest calendarRequest = getCalendarRequest(calendarRequestId);
        calendarRequestRepository.delete(calendarRequest);
    }

    @Transactional
    public Calendar uploadSchedule(CalendarUploadRequest request, User user) {
        if(user.getRole() == Role.GUEST || user.getRole() == Role.MEMBER) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

        String itemUrl = null;
        try {
            if(!request.getFile().isEmpty()) {
                itemUrl = s3Service.uploadFile(request.getFile(), Type.SCHEDULE);
            }
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        Calendar calendar = Calendar.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .date(request.getDate().plusDays(1))
                .type(request.getType())
                .user(user)
                .imageUrl(itemUrl)
                .link1(request.getLink1())
                .link2(request.getLink2())
                .build();

        return calendarRepository.save(calendar);
    }

    @Transactional
    public void updateSchedule(CalendarEditRequest request, User user) {
        if(user.getRole() == Role.GUEST || user.getRole() == Role.MEMBER) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

        Calendar calendar = calendarRepository.findById(request.getCalendarId())
                .orElseThrow(() -> new BaseException(CalendarExceptionCode.NOT_EXIST_CALENDAR_ID));

        String newImageUrl = null;
        try {
            if (request.getFile() != null && !request.getFile().isEmpty()) {
                newImageUrl = s3Service.uploadFile(request.getFile(), Type.SCHEDULE);
            }
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        if (request.getFile() == null || request.getFile().isEmpty()) {
            calendar.updateCalendar(request, null);
        } else {
            calendar.updateCalendar(request, newImageUrl);
        }
    }

    @Transactional
    public void deleteSchedule(Long calendarId, Integer userId) {
        User user = userService.getUserById(userId);

        if(user.getRole() == Role.GUEST || user.getRole() == Role.MEMBER) {
            throw new BaseException(CalendarExceptionCode.DO_NOT_HAVE_PERMISSION_TO_PROCESS);
        }

        calendarRepository.deleteById(calendarId);
    }

    public List<CalendarResponse> getCalendarBeforeList(Integer before) {
        List<Calendar> list = calendarRepository.findTop5ByOrder(LocalDate.now().minusDays(before), LocalDate.now());
        return list.stream().map(CalendarResponse::new).toList();
    }

    public List<CalendarResponse> getCalendarAfterList(Integer after) {
        List<Calendar> list = calendarRepository.findTop5ByOrder(LocalDate.now(), LocalDate.now().plusDays(after));
        Collections.reverse(list);
        return list.stream().map(CalendarResponse::new).toList();
    }
}
