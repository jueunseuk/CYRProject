package com.junsu.cyr.controller.announcement;

import com.junsu.cyr.model.announcement.AnnouncementConditionRequest;
import com.junsu.cyr.model.announcement.AnnouncementResponse;
import com.junsu.cyr.model.announcement.AnnouncementUploadRequest;
import com.junsu.cyr.service.announcement.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> getAnnouncement(@PathVariable Long announcementId, @RequestAttribute Integer userId) {
        AnnouncementResponse announcementResponse = announcementService.getAnnouncement(announcementId, userId);
        return ResponseEntity.ok(announcementResponse);
    }

    @GetMapping("/fixed")
    public ResponseEntity<List<AnnouncementResponse>> getAllAnnouncement() {
        List<AnnouncementResponse> announcementResponses = announcementService.getAnnouncementsByFixed();
        return ResponseEntity.ok(announcementResponses);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AnnouncementResponse>> getAllAnnouncement(@ModelAttribute AnnouncementConditionRequest condition, @RequestAttribute Integer userId) {
        Page<AnnouncementResponse> announcementResponses = announcementService.getAnnouncements(condition, userId);
        return ResponseEntity.ok(announcementResponses);
    }

    @PostMapping
    public ResponseEntity<AnnouncementResponse> uploadAnnouncement(@RequestBody AnnouncementUploadRequest request, @RequestAttribute Integer userId) {
        AnnouncementResponse announcementResponse = announcementService.uploadAnnouncement(request, userId);
        return ResponseEntity.ok(announcementResponse);
    }

    @PatchMapping("/{announcementId}")
    public ResponseEntity<AnnouncementResponse> updateAnnouncement(@RequestBody AnnouncementUploadRequest request, @PathVariable Long announcementId, @RequestAttribute Integer userId) {
        AnnouncementResponse announcementResponse = announcementService.updateAnnouncement(request, announcementId, userId);
        return ResponseEntity.ok(announcementResponse);
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long announcementId, @RequestAttribute Integer userId) {
        announcementService.deleteAnnouncement(announcementId, userId);
        return ResponseEntity.ok("success to delete announcement");
    }
}
