package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.rankings.*;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.ranking.CountRankingProjection;
import com.junsu.cyr.model.ranking.SumRankingProjection;
import com.junsu.cyr.repository.*;
import com.junsu.cyr.repository.projection.TotalCheerProjection;
import com.junsu.cyr.response.exception.code.RankingExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingAggregationService {

    private final RankingService rankingService;
    private final CheerSummaryRepository cheerSummaryRepository;
    private final UserService userService;
    private final AttendanceRepository attendanceRepository;
    private final ExperienceLogRepository experienceLogRepository;
    private final GlassLogRepository glassLogRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void refreshByPeriodWithScheduler(Refresh refresh) {
        switch (refresh) {
            case MIDNIGHT -> updateDailyRankings();
            case HOURLY -> updateHourlyRankings();
            case THREE_HOURLY -> updateThreeHourlyRankings();
            case TEN_MINUTES -> updateTenMinutesRankings();
        }
    }

    @Transactional
    public void refreshRanking(Type type, Period period, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastAdmin(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_ADMIN);
        }

        switch (type) {
            case CHEER -> updateCheerRanking(period);
            case ATTENDANCE -> updateAttendanceRanking(period);
            case EXP -> updateExperienceRanking(period);
            case GLASS -> updateGlassRanking(period);
            case POST -> updatePostRanking(period);
        }
    }

    public void updateDailyRankings() {
        updateCheerRanking(Period.TOTAL);
        updateAttendanceRanking(Period.TOTAL);
        updateGlassRanking(Period.TOTAL);
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

        if(period == Period.TOTAL) {
            List<TotalCheerProjection> cheerSummaries = cheerSummaryRepository.findTotalCheerRanking();
            RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.CHEER, period);

            long rank = 1;
            for(TotalCheerProjection cheerSummary : cheerSummaries) {
                User user = userService.getUserById(cheerSummary.getUserId());
                rankingService.createRanking(rankingCategory, user, rank++, cheerSummary.getSum());
            }
            return;
        }

        switch (period) {
            case DAILY -> start = now;
            case WEEKLY -> start = now.with(java.time.DayOfWeek.MONDAY);
            case MONTHLY -> start = now.withDayOfMonth(1);
            default -> throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        List<CheerSummary> top10 = cheerSummaryRepository
                .findTop10ByCheerSummaryId_DateBetweenOrderByCountDesc(start, now);

        RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.CHEER, period);

        long rank = 1;
        for (CheerSummary summary : top10) {
            User user = userService.getUserById(summary.getCheerSummaryId().getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
        }
    }

    public void updateAttendanceRanking(Period period) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);

        if(period != Period.MONTHLY && period != Period.TOTAL) {
            throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        if(period == Period.TOTAL) {
            List<User> users = userRepository.findTop10ByOrderByConsecutiveAttendanceCntDesc();

            RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.ATTENDANCE, period);

            long rank = 1;
            for (User user : users) {
                rankingService.createRanking(rankingCategory, user, rank++, Long.valueOf(user.getConsecutiveAttendanceCnt()));
            }
        } else {
            List<CountRankingProjection> attendances = attendanceRepository.findAllByAttendanceId_AttendedAtBetween(start, now);

            RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.ATTENDANCE, period);

            long rank = 1;
            for (CountRankingProjection summary : attendances) {
                User user = userService.getUserById(summary.getUserId());
                rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
            }
        }
    }

    public void updateExperienceRanking(Period period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;

        if(period == Period.TOTAL) {
            List<User> users = userRepository.findTop10ByOrderByEpxCntDesc();
            RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.EXP, period);
            long rank = 1;
            for (User user : users) {
                rankingService.createRanking(rankingCategory, user, rank++, user.getEpxCnt());
            }
            return;
        }

        switch (period) {
            case DAILY -> start = LocalDate.now().atStartOfDay();
            case WEEKLY -> start = now.with(java.time.DayOfWeek.MONDAY);
            case MONTHLY -> start = now.withDayOfMonth(1);
            default -> throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        List<SumRankingProjection> experienceRankings = experienceLogRepository.sumAllByCreatedAtBetween(start, now, PageRequest.of(0, 10));

        RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.EXP, period);

        long rank = 1;
        for (SumRankingProjection summary : experienceRankings) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getSum());
        }
    }

    public void updateGlassRanking(Period period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;

        switch (period) {
            case DAILY -> start = LocalDate.now().atStartOfDay();
            case TOTAL -> start = LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0, 0, 0);
            default -> throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        List<SumRankingProjection> glassRankings = glassLogRepository.sumAllByCreatedAtBetween(start, now, PageRequest.of(0, 10));

        RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.GLASS, period);

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

        RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.POST, period);

        long rank = 1;
        for (CountRankingProjection summary : postRankings) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
        }
    }
}
