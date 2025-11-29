package com.junsu.cyr.flow.event.comment;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.EventComment;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.EventCommentRepository;
import com.junsu.cyr.response.exception.code.EventCommentExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.event.EventCommentService;
import com.junsu.cyr.service.event.EventService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEventCommentFlow {

    private final UserService userService;
    private final EventService eventService;
    private final EventCommentService eventCommentService;
    private final EventCommentRepository eventCommentRepository;

    @Transactional
    public void deleteEventComment(Long eventId, Long eventCommentId, Integer userId) {
        User user = userService.getUserById(userId);
        Event event = eventService.findEventByEventId(eventId);
        EventComment eventComment = eventCommentService.findEventCommentByEventCommentId(eventCommentId);

        if(eventComment.getUser() != user) {
            throw new BaseException(EventCommentExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        eventCommentRepository.delete(eventComment);
        event.decreaseCommentCnt();
    }
}
