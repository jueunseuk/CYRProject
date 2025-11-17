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
public class AchievementNotificationUseCase {

    private final NotificationService notificationService;

    public void accomplishAchievement(User user, String name) {
        Notification notification = notificationService.createNotification(
                user,
                Type.ACHIEVEMENT,
                NotificationMessageConstant.format(NotificationMessageConstant.ACCOMPLISH_ACHIEVEMENT, name),
                null
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }
}
