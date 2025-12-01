package com.junsu.cyr.service.cheer;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.repository.CheerSummaryRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final UserRepository userRepository;
    private final CheerSummaryRepository cheerSummaryRepository;

    public Long getTotalCheer() {
        return cheerSummaryRepository.sumTotalCheers();
    }

    public Long getTotalCheer(LocalDate date) {
        return cheerSummaryRepository.sumByCheerSummaryId_Date(date);
    }

    public UserAssetDataResponse getAssetData(Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        UserAssetDataResponse response = new UserAssetDataResponse();
        response.setCurrent(user.getCheerCnt());

        LocalDate today = LocalDate.now();

        Long todayCount = countDuringPeriod(userId, today, today);
        response.setToday(todayCount);

        Long yesterdayCount = countDuringPeriod(userId, today.minusDays(1), today.minusDays(1));
        response.setIncrementByDay(todayCount - yesterdayCount);

        LocalDate monday = today.with(java.time.DayOfWeek.MONDAY);
        Long thisWeekCount = countDuringPeriod(userId, monday, today);
        response.setWeek(thisWeekCount);

        LocalDate lastMonday = monday.minusWeeks(1);
        LocalDate lastSunday = monday.minusDays(1);
        Long lastWeekCount = countDuringPeriod(userId, lastMonday, lastSunday);
        response.setIncrementByWeek(thisWeekCount - lastWeekCount);

        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        Long thisMonthCount = countDuringPeriod(userId, firstDayOfMonth, today);
        response.setMonth(thisMonthCount);

        LocalDate firstDayOfLastMonth = firstDayOfMonth.minusMonths(1);
        LocalDate lastDayOfLastMonth = firstDayOfMonth.minusDays(1);
        Long lastMonthCount = countDuringPeriod(userId, firstDayOfLastMonth, lastDayOfLastMonth);
        response.setIncrementByMonth(thisMonthCount - lastMonthCount);

        return response;
    }

    private Long countDuringPeriod(Integer userId, LocalDate start, LocalDate end) {
        List<CheerSummary> summaries = cheerSummaryRepository
                .findAllByCheerSummaryId_UserIdAndCheerSummaryId_DateBetween(userId, start, end);

        return summaries.stream()
                .mapToLong(CheerSummary::getCount)
                .sum();
    }

    public List<GraphResponse> getCheerHistory(Integer userId) {
        LocalDate today = LocalDate.now();
        LocalDate aMonthAgo = today.minusMonths(1);

        List<CheerSummary> summaries = cheerSummaryRepository.findAllByCheerSummaryId_UserIdAndCheerSummaryId_DateBetween(userId, aMonthAgo, today);

        List<GraphResponse.Point> points = summaries.stream()
                .map(cheerSummary -> new GraphResponse.Point(
                        cheerSummary.getCheerSummaryId().getDate(),
                        cheerSummary.getCount()
                ))
                .toList();

        return List.of(new GraphResponse("응원", points));
    }
}
