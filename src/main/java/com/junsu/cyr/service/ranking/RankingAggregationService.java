package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.rankings.*;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.ranking.CountRankingProjection;
import com.junsu.cyr.model.ranking.SumRankingProjection;
import com.junsu.cyr.repository.*;
import com.junsu.cyr.response.exception.code.RankingExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingAggregationService {

    private final RankingService rankingService;
    private final RankingCategoryService rankingCategoryService;
    private final CheerSummaryRepository cheerSummaryRepository;
    private final UserService userService;
    private final AttendanceRepository attendanceRepository;
    private final ExperienceLogRepository experienceLogRepository;
    private final GlassLogRepository glassLogRepository;
    private final PostRepository postRepository;

    @Transactional
    public void refreshByPeriod(Refresh refresh) {
        switch (refresh) {
            case MIDNIGHT -> updateDailyRankings();
            case HOURLY -> updateHourlyRankings();
            case THREE_HOURLY -> updateThreeHourlyRankings();
            case TEN_MINUTES -> updateTenMinutesRankings();
        }
    }

    public void updateDailyRankings() {

    }

    public void updateHourlyRankings() {
        updateAttendanceRanking(Period.MONTHLY);
        updateGlassRanking(Period.DAILY);
        updatePostRanking(Period.DAILY);
    }

    public void updateThreeHourlyRankings() {
        updateExperienceRanking(Period.DAILY);
    }

    public void updateTenMinutesRankings() {
        updateCheerRanking(Period.DAILY);
    }

    public void updateCheerRanking(Period period) {
        LocalDate now = LocalDate.now();
        LocalDate start;

        switch (period) {
            case DAILY -> start = now;
            case WEEKLY -> start = now.with(java.time.DayOfWeek.MONDAY);
            case MONTHLY -> start = now.withDayOfMonth(1);
            default -> throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        List<CheerSummary> top10 = cheerSummaryRepository
                .findTop10ByCheerSummaryId_DateBetweenOrderByCountDesc(start, now);

        RankingCategory rankingCategory = rankingCategoryService
                .getRankingCategoryByTypeAndPeriod(Type.CHEER, period);

        rankingService.deleteRankingByRankingCategory(rankingCategory);

        long rank = 1;
        for (CheerSummary summary : top10) {
            User user = userService.getUserById(summary.getCheerSummaryId().getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
        }
    }

    public void updateAttendanceRanking(Period period) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);

        if(period != Period.MONTHLY) {
            throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        List<CountRankingProjection> attendances = attendanceRepository.findAllByAttendanceId_AttendedAtBetween(start, now);

        RankingCategory rankingCategory = rankingCategoryService
                .getRankingCategoryByTypeAndPeriod(Type.ATTENDANCE, period);

        rankingService.deleteRankingByRankingCategory(rankingCategory);

        long rank = 1;
        for (CountRankingProjection summary : attendances) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
        }
    }

    public void updateExperienceRanking(Period period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;

        switch (period) {
            case DAILY -> start = now;
            case WEEKLY -> start = now.with(java.time.DayOfWeek.MONDAY);
            case MONTHLY -> start = now.withDayOfMonth(1);
            default -> throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        List<SumRankingProjection> experienceRankings = experienceLogRepository.sumAllByCreatedAtBetween(start, now, PageRequest.of(0, 10));

        RankingCategory rankingCategory = rankingCategoryService
                .getRankingCategoryByTypeAndPeriod(Type.EXP, period);

        rankingService.deleteRankingByRankingCategory(rankingCategory);

        long rank = 1;
        for (SumRankingProjection summary : experienceRankings) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getSum());
        }
    }

    public void updateGlassRanking(Period period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDate.now().atStartOfDay();

        List<SumRankingProjection> glassRankings = glassLogRepository.sumAllByCreatedAtBetween(start, now, PageRequest.of(0, 10));

        RankingCategory rankingCategory = rankingCategoryService
                .getRankingCategoryByTypeAndPeriod(Type.GLASS, period);

        rankingService.deleteRankingByRankingCategory(rankingCategory);

        long rank = 1;
        for (SumRankingProjection summary : glassRankings) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getSum());
        }
    }

    public void updatePostRanking(Period period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDate.now().atStartOfDay();

        List<CountRankingProjection> postRankings = postRepository.sumAllByCreatedAtBetween(start, now, PageRequest.of(0, 10));

        RankingCategory rankingCategory = rankingCategoryService
                .getRankingCategoryByTypeAndPeriod(Type.POST, period);

        rankingService.deleteRankingByRankingCategory(rankingCategory);

        long rank = 1;
        for (CountRankingProjection summary : postRankings) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
        }
    }
}
