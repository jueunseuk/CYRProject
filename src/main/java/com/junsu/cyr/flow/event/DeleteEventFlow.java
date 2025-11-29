package com.junsu.cyr.flow.event;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.EventRepository;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.event.EventCommentService;
import com.junsu.cyr.service.event.EventService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEventFlow {

    private final UserService userService;
    private final EventService eventService;
    private final EventCommentService eventCommentService;
    private final EventRepository eventRepository;

    @Transactional
    public void deleteEvent(Long eventId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        Event event = eventService.findEventByEventId(eventId);
        eventCommentService.deleteAllEventComment(event);
        eventRepository.delete(event);
    }
}
