package com.junsu.cyr.controller.event;

import com.junsu.cyr.model.event.EventCommentResponse;
import com.junsu.cyr.model.event.EventCommentUploadRequest;
import com.junsu.cyr.service.event.EventCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/{eventId}/comment")
public class EventCommentController {

    private final EventCommentService eventCommentService;

    @GetMapping("/all")
    public ResponseEntity<List<EventCommentResponse>> getAllComments(@PathVariable Long eventId, @RequestAttribute Integer userId) {
        List<EventCommentResponse> eventCommentResponses = eventCommentService.getAllEventCommentByEventId(eventId, userId);
        return ResponseEntity.ok(eventCommentResponses);
    }

    @PostMapping
    public ResponseEntity<EventCommentResponse> uploadComment(@PathVariable Long eventId, @ModelAttribute EventCommentUploadRequest request, @RequestAttribute Integer userId) {
        EventCommentResponse eventCommentResponse = eventCommentService.uploadComment(eventId, request, userId);
        return ResponseEntity.ok(eventCommentResponse);
    }

    @DeleteMapping("/{eventCommentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long eventId, @PathVariable Long eventCommentId, @RequestAttribute Integer userId) {
        eventCommentService.deleteEventComment(eventId, eventCommentId, userId);
        return ResponseEntity.ok("success to delete event comment");
    }
}
