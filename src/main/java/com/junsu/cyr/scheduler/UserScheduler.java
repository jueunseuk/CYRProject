package com.junsu.cyr.scheduler;

import com.junsu.cyr.flow.user.maintenance.UserCleanupFlow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserScheduler {

    private final UserCleanupFlow userCleanupFlow;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void generateHourlyStatistic() {
        log.info("[UserScheduler] Find users who have left a week ago");

        try {
            Integer size = userCleanupFlow.userCleanup();
            log.info("[UserScheduler] Deleted {} users after one week", size);
        } catch (Exception e) {
            log.error("[UserScheduler] Error deleting user", e);
        }
    }
}
