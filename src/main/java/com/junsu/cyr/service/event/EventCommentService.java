package com.junsu.cyr.service.event;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.EventComment;
import com.junsu.cyr.domain.images.Image;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.event.EventCommentResponse;
import com.junsu.cyr.model.event.EventCommentUploadRequest;
import com.junsu.cyr.repository.EventCommentRepository;
import com.junsu.cyr.response.exception.code.EventCommentExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.image.ImageService;
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
    private final ImageService imageService;

    public EventComment findEventCommentByEventCommentId(Long eventCommentId) {
        return eventCommentRepository.findById(eventCommentId)
                .orElseThrow(() -> new BaseException(EventCommentExceptionCode.NOT_FOUND_EVENT));
    }

    public List<EventCommentResponse> getAllEventCommentByEventId(Long eventId, Integer userId) {
        userService.getUserById(userId);

        Event event = eventService.findEventByEventId(eventId);

        List<EventComment> eventComments = eventCommentRepository.findAllByEventOrderByCreatedAtAsc(event);

        return eventComments.stream().map(EventCommentResponse::new).toList();
    }

    @Transactional
    public EventCommentResponse uploadComment(Long eventId, EventCommentUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Event event = eventService.findEventByEventId(eventId);

        if(!event.getUseComment()) {
            throw new BaseException(EventCommentExceptionCode.COMMENT_ARE_NOT_AVAILABLE);
        }

        if(eventCommentRepository.existsByUserAndEvent(user, event)) {
            throw new BaseException(EventCommentExceptionCode.ALREADY_UPLOAD_COMMENT);
        }

        isValidUploadData(request);

        EventComment eventComment = EventComment.builder()
                .user(user)
                .event(event)
                .content(request.getContent())
                .build();
        eventCommentRepository.save(eventComment);
        event.increaseCommentCnt();

        Image image;
        if(request.getFile() != null) {
            image = imageService.uploadImage(request.getFile(), eventComment.getEventCommentId(), Type.EVENT_COMMENT);
            eventComment.updateCaptureUrl(image.getUrl());
        }

        return new EventCommentResponse(eventComment);
    }

    @Transactional
    public void deleteEventComment(Long eventId, Long eventCommentId, Integer userId) {
        User user = userService.getUserById(userId);
        Event event = eventService.findEventByEventId(eventId);
        EventComment eventComment = findEventCommentByEventCommentId(eventCommentId);

        if(eventComment.getUser() != user) {
            throw new BaseException(EventCommentExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        eventCommentRepository.delete(eventComment);
        event.decreaseCommentCnt();
    }

    @Transactional
    public void deleteAllEventComment(Event event) {
        List<EventComment> eventComments = eventCommentRepository.findAllByEvent(event);
        eventCommentRepository.deleteAll(eventComments);
    }

    private void isValidUploadData(EventCommentUploadRequest request) {
        if(request.getContent() == null || request.getContent().length() < 5) {
            throw new BaseException(EventCommentExceptionCode.TOO_SHORT_COMMENT);
        }
    }
}
