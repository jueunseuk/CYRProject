package com.junsu.cyr.service.notification;

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
        log.info("[NotificationScheduler] {} => 한 달 지난 알림 삭제", LocalDateTime.now());

        try {
            notificationService.deleteBeforeNotification(30);
            log.info("{} => 알림 삭제 완료", LocalDateTime.now());
        } catch (Exception e) {
            log.error("❌ 알림 삭제 중 에러 발생", e);
        }
    }
}
