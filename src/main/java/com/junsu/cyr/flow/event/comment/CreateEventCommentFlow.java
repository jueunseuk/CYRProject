package com.junsu.cyr.flow.event.comment;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.EventComment;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.event.EventCommentResponse;
import com.junsu.cyr.model.event.EventCommentUploadRequest;
import com.junsu.cyr.repository.EventCommentRepository;
import com.junsu.cyr.response.exception.code.EventCommentExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.event.EventCommentService;
import com.junsu.cyr.service.event.EventService;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateEventCommentFlow {

    private final UserService userService;
    private final EventService eventService;
    private final EventCommentRepository eventCommentRepository;
    private final S3Service s3Service;
    private final EventCommentService eventCommentService;

    @Transactional
    public EventCommentResponse createEventComment(Long eventId, EventCommentUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Event event = eventService.findEventByEventId(eventId);

        if(!event.getUseComment()) {
            throw new BaseException(EventCommentExceptionCode.COMMENT_ARE_NOT_AVAILABLE);
        }

        if(eventCommentRepository.existsByUserAndEvent(user, event)) {
            throw new BaseException(EventCommentExceptionCode.ALREADY_UPLOAD_COMMENT);
        }

        EventComment eventComment = eventCommentService.createEventComment(user, event, request.getContent());

        if(request.getFile() != null) {
            String imageUrl = s3Service.uploadFile(request.getFile(), Type.EVENT_COMMENT);
            eventComment.updateImageUrl(imageUrl);
        }

        event.increaseCommentCnt();
        return new EventCommentResponse(eventComment);
    }
}
