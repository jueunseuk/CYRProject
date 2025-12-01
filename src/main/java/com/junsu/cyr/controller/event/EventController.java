package com.junsu.cyr.controller.event;

import com.junsu.cyr.flow.event.CreateEventFlow;
import com.junsu.cyr.flow.event.DeleteEventFlow;
import com.junsu.cyr.model.event.EventConditionRequest;
import com.junsu.cyr.model.event.EventResponse;
import com.junsu.cyr.model.event.EventUpdateRequest;
import com.junsu.cyr.model.event.EventUploadRequest;
import com.junsu.cyr.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;
    private final CreateEventFlow createEventFlow;
    private final DeleteEventFlow deleteEventFlow;

    @GetMapping("/list")
    public ResponseEntity<Page<EventResponse>> getAllEvents(@ModelAttribute EventConditionRequest request, @RequestAttribute Integer userId) {
        Page<EventResponse> eventResponses = eventService.findEventByType(request, userId);
        return ResponseEntity.ok(eventResponses);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId, @RequestAttribute Integer userId) {
        EventResponse eventResponse = eventService.findEvent(eventId, userId);
        return ResponseEntity.ok(eventResponse);
    }

    @PostMapping
    public ResponseEntity<EventResponse> uploadEvent(@RequestBody EventUploadRequest request, @RequestAttribute Integer userId) {
        EventResponse eventResponse = createEventFlow.createEvent(request, userId);
        return ResponseEntity.ok(eventResponse);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long eventId, @RequestBody EventUpdateRequest request, @RequestAttribute Integer userId) {
        EventResponse eventResponse = eventService.updateEvent(eventId, request, userId);
        return ResponseEntity.ok(eventResponse);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId, @RequestAttribute Integer userId) {
        deleteEventFlow.deleteEvent(eventId, userId);
        return ResponseEntity.ok("success to delete event and event comment");
    }
}
