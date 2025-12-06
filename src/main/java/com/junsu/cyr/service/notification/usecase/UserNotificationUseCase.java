package com.junsu.cyr.service.notification.usecase;

import com.junsu.cyr.constant.NotificationMessageConstant;
import com.junsu.cyr.domain.notification.Notification;
import com.junsu.cyr.domain.notification.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNotificationUseCase {

    private final NotificationService notificationService;

    public void refreshActivity(User user) {
        Notification notification = notificationService.createNotification(
                user,
                Type.ACHIEVEMENT,
                NotificationMessageConstant.REFRESH_ACTIVITY_FORCE,
                null
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }

    public void register(User user) {
        Notification notification = notificationService.createNotification(
                user,
                Type.SYSTEM,
                NotificationMessageConstant.NEW_REGISTER_OCCURRED,
                null
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }

    public void login(User user) {
        Notification notification = notificationService.createNotification(
                user,
                Type.SYSTEM,
                NotificationMessageConstant.NEW_LOGIN_OCCURRED,
                null
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }
}
