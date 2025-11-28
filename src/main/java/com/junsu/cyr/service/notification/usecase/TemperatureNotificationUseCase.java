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
public class TemperatureNotificationUseCase {

    private final NotificationService notificationService;

    public void temperatureMaximumReached(User user) {
        Notification notification = notificationService.createNotification(
                user,
                Type.SYSTEM,
                NotificationMessageConstant.TEMPERATURE_MAXIMUM,
                null
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }

    public void temperatureMaximumReachAndCraftGlass(User user) {
        Notification notification = notificationService.createNotification(
                user,
                Type.SYSTEM,
                NotificationMessageConstant.TEMPERATURE_MAXIMUM_AND_CRAFT_GLASS,
                null
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }

    public void additionalTemperatureReached(User user) {
        Notification notification = notificationService.createNotification(
                user,
                Type.SYSTEM,
                NotificationMessageConstant.RECEIVED_ADDITIONAL_TEMPERATURE,
                null
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }
}
