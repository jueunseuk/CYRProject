package com.junsu.cyr.service.event;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.EventComment;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.event.EventCommentResponse;
import com.junsu.cyr.repository.EventCommentRepository;
import com.junsu.cyr.response.exception.code.EventCommentExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventCommentService {

    private final EventCommentRepository eventCommentRepository;
    private final UserService userService;
    private final EventService eventService;

    public EventComment findEventCommentByEventCommentId(Long eventCommentId) {
        return eventCommentRepository.findById(eventCommentId)
                .orElseThrow(() -> new BaseException(EventCommentExceptionCode.NOT_FOUND_EVENT));
    }

    public EventComment createEventComment(User user, Event event, String content) {
        if(content== null || content.length() < 5) {
            throw new BaseException(EventCommentExceptionCode.TOO_SHORT_COMMENT);
        }

        EventComment eventComment = EventComment.builder()
                .user(user)
                .event(event)
                .content(content)
                .build();

        return eventCommentRepository.save(eventComment);
    }

    public List<EventCommentResponse> getAllEventCommentByEventId(Long eventId, Integer userId) {
        userService.getUserById(userId);

        Event event = eventService.findEventByEventId(eventId);

        List<EventComment> eventComments = eventCommentRepository.findAllByEventOrderByCreatedAtAsc(event);

        return eventComments.stream().map(EventCommentResponse::new).toList();
    }

    @Transactional
    public void deleteAllEventComment(Event event) {
        List<EventComment> eventComments = eventCommentRepository.findAllByEvent(event);
        eventCommentRepository.deleteAll(eventComments);
    }
}
