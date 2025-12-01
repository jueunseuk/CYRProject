package com.junsu.cyr.controller.notification;

import com.junsu.cyr.flow.notify.NotifyToAllUserFlow;
import com.junsu.cyr.model.notification.NotificationAllRequest;
import com.junsu.cyr.model.notification.NotificationResponse;
import com.junsu.cyr.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotifyToAllUserFlow notifyToAllUserFlow;

    @PatchMapping("/read/all")
    public ResponseEntity<String> readAllNotification(@RequestAttribute Integer userId) {
        notificationService.readNotificationByUser(userId);
        return ResponseEntity.ok("success to read all notification");
    }

    @GetMapping("/unread/all")
    public ResponseEntity<List<NotificationResponse>> getAllUnreadNotification(@RequestAttribute Integer userId) {
        List<NotificationResponse> notificationResponses = notificationService.getUnreadNotificationByUser(userId);
        return ResponseEntity.ok(notificationResponses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationResponse>> getAllNotification(@RequestAttribute Integer userId) {
        List<NotificationResponse> notificationResponses = notificationService.getNotificationByUser(userId);
        return ResponseEntity.ok(notificationResponses);
    }

    @PostMapping("/all")
    public ResponseEntity<String> notifyAllUser(@RequestBody NotificationAllRequest request, @RequestAttribute Integer userId) {
        notifyToAllUserFlow.notifyToAllUser(request, userId);
        return ResponseEntity.ok("success to notify all user");
    }
}
