package com.junsu.cyr.repository;

import com.junsu.cyr.domain.notification.Notification;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserAndIsRead(User user, boolean isRead);

    void deleteAllByIsReadAndCreatedAtBetween(boolean isRead, LocalDateTime start, LocalDateTime end);
}
