package com.junsu.cyr.service.event;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.Status;
import com.junsu.cyr.domain.events.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.event.EventConditionRequest;
import com.junsu.cyr.model.event.EventResponse;
import com.junsu.cyr.model.event.EventUpdateRequest;
import com.junsu.cyr.model.event.EventUploadRequest;
import com.junsu.cyr.repository.EventRepository;
import com.junsu.cyr.response.exception.code.EventExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    public Event findEventByEventId(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(EventExceptionCode.NOT_FOUND_EVENT));
    }

    public Page<EventResponse> findEventByType(EventConditionRequest request, Integer userId) {
        userService.getUserById(userId);

        Pageable pageable = PageableMaker.of(request.getPage(), request.getSize(), request.getSort(), request.getDirection());
        Page<Event> events;
        if(request.getType() == null && request.getStatus() == null) {
            events = eventRepository.findAllByLocked(Boolean.FALSE, pageable);
        } else if(request.getType() == null) {
            events = eventRepository.findAllByLockedAndStatus(Boolean.FALSE, request.getStatus(), pageable);
        } else if(request.getStatus() == null) {
            events = eventRepository.findAllByLockedAndType(Boolean.FALSE, request.getType(), pageable);
        } else {
            events = eventRepository.findAllByLockedAndStatusAndType(Boolean.FALSE, request.getStatus(), request.getType(), pageable);
        }

        return events.map(EventResponse::new);
    }

    @Transactional
    public EventResponse findEvent(Long eventId, Integer userId) {
        userService.getUserById(userId);
        Event event = findEventByEventId(eventId);

        event.increaseViewCnt();

        return new EventResponse(event);
    }

    @Transactional
    public EventResponse uploadEvent(EventUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        isValidUploadData(request);

        Event event = Event.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .type(request.getType())
                .maxUser(request.getType() == Type.FIRSTCOME ? request.getMaxUser() : Integer.MAX_VALUE)
                .useComment(request.getUseComment())
                .fixed(request.getFixed() || Boolean.FALSE)
                .locked(request.getLocked() || Boolean.FALSE)
                .commentCnt(0L)
                .status(request.getStatus())
                .viewCnt(0L)
                .closedAt(LocalDateTime.parse(request.getClosedAt()))
                .build();
        eventRepository.save(event);

        return new EventResponse(event);
    }

    private void isValidUploadData(EventUploadRequest request) {
        if(request.getTitle() == null || request.getTitle().length() < 5) {
            throw new BaseException(EventExceptionCode.TOO_SHORT_TITLE);
        }
        if(request.getContent() == null || request.getContent().length() < 5) {
            throw new BaseException(EventExceptionCode.TOO_SHORT_CONTENT);
        }
        if(request.getType() == null) {
            throw new BaseException(EventExceptionCode.INVALID_EVENT_TYPE);
        }
        if(request.getClosedAt() == null) {
            throw new BaseException(EventExceptionCode.INVALID_DEADLINE);
        }
    }

    @Transactional
    public EventResponse updateEvent(Long eventId, EventUpdateRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        if(request.getType() == null && request.getStatus() == null) {
            throw new BaseException(EventExceptionCode.INVALID_REQUEST);
        }

        Event event = findEventByEventId(eventId);

        if(request.getType() != null) {
            event.updateType(request.getType());
        }

        if(request.getStatus() != null) {
            event.updateStatus(request.getStatus());
        }

        return new EventResponse(event);
    }
}
