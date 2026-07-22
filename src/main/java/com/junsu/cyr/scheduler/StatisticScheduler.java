package com.junsu.cyr.scheduler;

import com.junsu.cyr.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticScheduler {

    private final StatisticService statisticService;

    @Scheduled(cron = "0 0 */2 * * *")
    @Transactional
    public void generateHourlyStatistic() {
        log.info("Start Statistics Generation Schedule");

        try {
            statisticService.createStatistic();
            log.info("Stats saved complete");
        } catch (Exception e) {
            log.error("Error generating statistics", e);
        }
    }
}
