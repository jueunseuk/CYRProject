package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.rankings.*;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.ranking.CountRankingProjection;
import com.junsu.cyr.model.ranking.SumRankingProjection;
import com.junsu.cyr.repository.*;
import com.junsu.cyr.repository.projection.TotalCheerProjection;
import com.junsu.cyr.response.exception.code.RankingExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.glass.GlassService;
import com.junsu.cyr.service.notification.usecase.RankingNotificationUseCase;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final GlassService glassService;
    private final RankingNotificationUseCase rankingNotificationUseCase;
    private final RankingRepository rankingRepository;

    @Transactional
    public void refreshByPeriodWithScheduler(Refresh refresh) {
        Set<Integer> userList = new HashSet<>(rankingRepository.findUser_UserId());

        switch (refresh) {
            case MIDNIGHT -> updateDailyRankings(userList);
            case HOURLY -> updateHourlyRankings(userList);
            case THREE_HOURLY -> updateThreeHourlyRankings(userList);
            case TEN_MINUTES -> updateTenMinutesRankings(userList);
        }
    }

    @Transactional
    public void refreshRanking(Type type, Period period, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastAdmin(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_ADMIN);
        }

        Set<Integer> userList = new HashSet<>(rankingRepository.findUser_UserId());

        switch (type) {
            case CHEER -> updateCheerRanking(period, userList);
            case ATTENDANCE -> updateAttendanceRanking(period, userList);
            case EXP -> updateExperienceRanking(period, userList);
            case GLASS -> updateGlassRanking(period, userList);
            case POST -> updatePostRanking(period, userList);
        }
    }

    public void updateDailyRankings(Set<Integer> userList) {
        updateCheerRanking(Period.TOTAL, userList);
        updateAttendanceRanking(Period.TOTAL, userList);
        updateGlassRanking(Period.TOTAL, userList);
    }

    public void updateHourlyRankings(Set<Integer> userList) {
        updateAttendanceRanking(Period.MONTHLY, userList);
        updateGlassRanking(Period.DAILY, userList);
        updatePostRanking(Period.DAILY, userList);
    }

    public void updateThreeHourlyRankings(Set<Integer> userList) {
        updateExperienceRanking(Period.DAILY, userList);
    }

    public void updateTenMinutesRankings(Set<Integer> userList) {
        updateCheerRanking(Period.DAILY, userList);
    }

    public void updateCheerRanking(Period period, Set<Integer> userList) {
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
            case WEEKLY -> start = now.with(DayOfWeek.MONDAY);
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
            if(!userList.contains(user.getUserId())) {
                rankingNotificationUseCase.enterRanking(user);
            }
        }
    }

    public void updateAttendanceRanking(Period period, Set<Integer> userList) {
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
                if(!userList.contains(user.getUserId())) {
                    rankingNotificationUseCase.enterRanking(user);
                }
            }
        }
    }

    public void updateExperienceRanking(Period period, Set<Integer> userList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;

        if(period == Period.TOTAL) {
            List<User> users = userRepository.findTop10ByOrderByEpxCntDesc();
            RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.EXP, period);
            long rank = 1;
            for (User user : users) {
                rankingService.createRanking(rankingCategory, user, rank++, user.getEpxCnt());
                if(!userList.contains(user.getUserId())) {
                    rankingNotificationUseCase.enterRanking(user);
                }
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
            if(!userList.contains(user.getUserId())) {
                rankingNotificationUseCase.enterRanking(user);
            }
        }
    }

    public void updateGlassRanking(Period period, Set<Integer> userList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;

        switch (period) {
            case DAILY -> start = LocalDate.now().atStartOfDay();
            case TOTAL -> start = LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0, 0, 0);
            default -> throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        Glass glass = glassService.getGlass(1);
        List<CountRankingProjection> glassRankings = glassLogRepository.countAllByGlassCreatedAtBetween(glass, start, now, PageRequest.of(0, 10));

        RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.GLASS, period);

        long rank = 1;
        for (CountRankingProjection summary : glassRankings) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
            if(!userList.contains(user.getUserId())) {
                rankingNotificationUseCase.enterRanking(user);
            }
        }
    }

    public void updatePostRanking(Period period, Set<Integer> userList) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDate.now().atStartOfDay();

        List<CountRankingProjection> postRankings = postRepository.sumAllByCreatedAtBetween(start, now, PageRequest.of(0, 10));

        RankingCategory rankingCategory = rankingService.deleteRankingByTypeAndPeriod(Type.POST, period);

        long rank = 1;
        for (CountRankingProjection summary : postRankings) {
            User user = userService.getUserById(summary.getUserId());
            rankingService.createRanking(rankingCategory, user, rank++, summary.getCount());
            if(!userList.contains(user.getUserId())) {
                rankingNotificationUseCase.enterRanking(user);
            }
        }
    }
}
