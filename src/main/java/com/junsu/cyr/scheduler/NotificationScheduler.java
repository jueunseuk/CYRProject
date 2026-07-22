package com.junsu.cyr.scheduler;

import com.junsu.cyr.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteBeforeAMonth() {
        log.info("Delete a month old notification");

        try {
            notificationService.deleteBeforeNotification(30);
            log.info("Clear notification complete");
        } catch (Exception e) {
            log.error("Error deleting notification", e);
        }
    }
}
