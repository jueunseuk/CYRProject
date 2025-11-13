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
public class ChatNotificationUseCase {

    private final NotificationService notificationService;

    public void invitedChatRoom(User user, User other) {
        Notification notification = notificationService.createNotification(
                user,
                Type.CHAT,
                NotificationMessageConstant.format(NotificationMessageConstant.CHAT_INVITATION, other.getNickname()),
                Long.valueOf(other.getUserId())
        );

        notificationService.pushNotification(user.getUserId(), notification);
    }
}
