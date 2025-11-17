package com.junsu.cyr.service.notification;

import com.junsu.cyr.domain.notification.Notification;
import com.junsu.cyr.domain.notification.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.notification.NotificationResponse;
import com.junsu.cyr.repository.NotificationRepository;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    @Transactional
    public Notification createNotification(User user, Type type, String message, Long targetId) {
        Notification notification = Notification.builder()
            .user(user)
            .type(type)
            .message(message)
            .targetId(targetId)
            .isRead(false)
            .build();

        return notificationRepository.save(notification);
    }

    public void pushNotification(Integer userId, Notification notification) {
        messagingTemplate.convertAndSend("/topic/notification/" + userId, new NotificationResponse(notification));
    }

    @Transactional
    public void readNotificationByUser(Integer userId) {
        User user = userService.getUserById(userId);

        List<Notification> notifications = notificationRepository.findAllByUserAndIsRead(user, false);

        for(Notification n : notifications) {
            n.readNotification();
        }
    }

    @Transactional
    public void deleteBeforeNotification(Integer day) {
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.now().minusDays(day);

        notificationRepository.deleteAllByIsReadAndCreatedAtBetween(true, start, end);
    }

    public List<NotificationResponse> getUnreadNotificationByUser(Integer userId) {
        User user = userService.getUserById(userId);

        List<Notification> notifications = notificationRepository.findAllByUserAndIsRead(user, false);

        return notifications.stream().map(NotificationResponse::new).toList();
    }

    public List<NotificationResponse> getNotificationByUser(Integer userId) {
        User user = userService.getUserById(userId);

        List<Notification> notifications = notificationRepository.findAllByUserOrderByCreatedAtDesc(user);

        return notifications.stream().map(NotificationResponse::new).toList();
    }
}
