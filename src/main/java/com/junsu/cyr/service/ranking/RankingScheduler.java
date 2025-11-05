package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.rankings.Refresh;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingScheduler {

    private final RankingAggregationService rankingAggregationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void refreshDailyRankings() {
        executeRefresh(Refresh.MIDNIGHT);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void refreshHourlyRankings() {
        executeRefresh(Refresh.HOURLY);
    }

    @Scheduled(cron = "0 0 */3 * * *")
    public void refresh3HourRankings() {
        executeRefresh(Refresh.THREE_HOURLY);
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void refresh10MinRankings() {
        executeRefresh(Refresh.TEN_MINUTES);
    }

    private void executeRefresh(Refresh refreshType) {
        rankingAggregationService.refreshByPeriod(refreshType);
    }
}
