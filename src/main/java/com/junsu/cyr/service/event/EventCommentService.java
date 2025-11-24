package com.junsu.cyr.service.event;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.EventComment;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.event.EventCommentResponse;
import com.junsu.cyr.model.event.EventCommentUploadRequest;
import com.junsu.cyr.repository.EventCommentRepository;
import com.junsu.cyr.response.exception.code.EventCommentExceptionCode;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.image.S3Service;
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
    private final S3Service s3Service;

    public EventComment findEventCommentByEventCommentId(Long eventCommentId) {
        return eventCommentRepository.findById(eventCommentId)
                .orElseThrow(() -> new BaseException(EventCommentExceptionCode.NOT_FOUND_EVENT));
    }

    public List<EventCommentResponse> getAllEventCommentByEventId(Long eventId, Integer userId) {
        userService.getUserById(userId);

        Event event = eventService.findEventByEventId(eventId);

        List<EventComment> eventComments = eventCommentRepository.findAllByEvent(event);

        return eventComments.stream().map(EventCommentResponse::new).toList();
    }

    @Transactional
    public EventCommentResponse uploadComment(Long eventId, EventCommentUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Event event = eventService.findEventByEventId(eventId);

        if(!event.getUseComment()) {
            throw new BaseException(EventCommentExceptionCode.COMMENT_ARE_NOT_AVAILABLE);
        }

        isValidUploadData(request);

        String imageUrl = null;
        try {
            if(request.getFile() != null) {
                imageUrl = s3Service.uploadFile(request.getFile(), Type.EVENT_COMMENT);
            }
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        EventComment eventComment = EventComment.builder()
                .user(user)
                .event(event)
                .content(request.getContent())
                .imageUrl(imageUrl)
                .build();
        eventCommentRepository.save(eventComment);
        event.increaseCommentCnt();

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
