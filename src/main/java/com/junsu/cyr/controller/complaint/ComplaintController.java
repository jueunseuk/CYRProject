package com.junsu.cyr.controller.complaint;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.complaint.*;
import com.junsu.cyr.service.complaint.ComplaintService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/complaint")
public class ComplaintController {

    private final UserService userService;
    private final ComplaintService complaintService;

    @GetMapping("/{complaintId}")
    public ResponseEntity<ComplaintResponse> getComplaint(@PathVariable Long complaintId, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        ComplaintResponse complaintResponse = complaintService.getComplaint(complaintId, user);
        return ResponseEntity.ok(complaintResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ComplaintListResponse>> getComplaintList(@ModelAttribute ComplaintConditionRequest condition, @RequestAttribute Integer userId) {
        userService.getUserById(userId);
        Page<ComplaintListResponse> complaintListResponse = complaintService.getComplaintList(condition);
        return ResponseEntity.ok(complaintListResponse);
    }

    @PatchMapping("/{complaintId}/accept")
    public ResponseEntity<String> acceptComplaint(@RequestBody ComplaintProcessRequest request, @PathVariable Long complaintId, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        complaintService.acceptComplaint(complaintId, request.getMessage(), user);
        return ResponseEntity.ok("success to accepting complaint");
    }

    @PatchMapping("/{complaintId}/reject")
    public ResponseEntity<String> rejectComplaint(@RequestBody ComplaintProcessRequest request, @PathVariable Long complaintId, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        complaintService.rejectComplaint(complaintId, request.getMessage(), user);
        return ResponseEntity.ok("success to rejecting complaint");
    }

    @PostMapping
    public ResponseEntity<String> uploadComplaint(@RequestBody ComplaintRequest request, @RequestAttribute Integer userId) {
        User user = userService.getUserById(userId);
        complaintService.uploadComplaint(request, user);
        return ResponseEntity.ok("success to upload complaint");
    }
}
