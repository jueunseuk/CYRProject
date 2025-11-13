package com.junsu.cyr.model.notification;

import com.junsu.cyr.domain.notification.Notification;
import com.junsu.cyr.domain.notification.Type;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long notificationId;
    private Type type;
    private Long targetId;
    private String message;
    private LocalDateTime createdAt;

    public NotificationResponse(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.type = notification.getType();
        this.targetId = notification.getTargetId();
        this.message = notification.getMessage();
        this.createdAt = notification.getCreatedAt();
    }
}
