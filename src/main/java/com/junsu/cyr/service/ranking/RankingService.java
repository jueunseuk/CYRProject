package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.rankings.Period;
import com.junsu.cyr.domain.rankings.Ranking;
import com.junsu.cyr.domain.rankings.RankingCategory;
import com.junsu.cyr.domain.rankings.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.ranking.RankingConditionRequest;
import com.junsu.cyr.model.ranking.RankingResponse;
import com.junsu.cyr.repository.RankingRepository;
import com.junsu.cyr.response.exception.code.RankingExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserNicknameSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final RankingCategoryService rankingCategoryService;
    private final UserNicknameSettingService userNicknameSettingService;

    public Ranking getRankingByRankId(Integer rankId) {
        return rankingRepository.findById(rankId)
                .orElseThrow(() -> new BaseException(RankingExceptionCode.NOT_FOUND_RANKING));
    }

    @Transactional
    public Ranking createRanking(RankingCategory rankingCategory, User user, Long score) {
        Ranking ranking = Ranking.builder()
                .rankingCategory(rankingCategory)
                .user(user)
                .priority(1L)
                .score(score)
                .build();

        return rankingRepository.save(ranking);
    }

    @Transactional
    public void createRanking(RankingCategory rankingCategory, User user, Long priority, Long score) {
        Ranking ranking = Ranking.builder()
                .rankingCategory(rankingCategory)
                .user(user)
                .priority(priority)
                .score(score)
                .build();

        rankingRepository.save(ranking);
    }

    @Transactional
    public RankingCategory deleteRankingByTypeAndPeriod(Type type, Period period) {
        RankingCategory rankingCategory = rankingCategoryService.getRankingCategoryByTypeAndPeriod(type, period);
        rankingRepository.deleteAllByRankingCategory(rankingCategory);
        return rankingCategory;
    }

    public List<RankingResponse> getRanking(Type type, RankingConditionRequest condition) {
        if(type == null) {
            throw new BaseException(RankingExceptionCode.INVALID_TYPE);
        } else if(condition.getPeriod() == null) {
            throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        RankingCategory rankingCategory = rankingCategoryService.getRankingCategoryByTypeAndPeriod(type, condition.getPeriod());

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<Ranking> rankingResponses = rankingRepository.findAllByRankingCategory(rankingCategory, pageable);

        return rankingResponses.stream().map(ranking -> new RankingResponse(ranking, userNicknameSettingService.getUserNicknameColor(ranking.getUser()))).toList();
    }

    public List<RankingResponse> getSummaryRanking() {
        RankingConditionRequest condition = new RankingConditionRequest(0, 3, "priority", "ASC", Period.TOTAL);
        List<RankingResponse> rankingResponses = new ArrayList<>(getRanking(Type.ATTENDANCE, condition));

        condition.setPeriod(Period.TOTAL);
        rankingResponses.addAll(getRanking(Type.CHEER, condition));

        condition.setPeriod(Period.DAILY);
        rankingResponses.addAll(getRanking(Type.CHEER, condition));

        condition.setPeriod(Period.TOTAL);
        rankingResponses.addAll(getRanking(Type.GLASS, condition));

        return rankingResponses;
    }
}
