package com.junsu.cyr.flow.event;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.event.EventResponse;
import com.junsu.cyr.model.event.EventUploadRequest;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.event.EventService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateEventFlow {

    private final UserService userService;
    private final EventService eventService;

    @Transactional
    public EventResponse createEvent(EventUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        Event event = eventService.createEvent(request, user);
        return new EventResponse(event);
    }
}
