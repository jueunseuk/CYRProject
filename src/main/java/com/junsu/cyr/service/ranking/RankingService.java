package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.rankings.Ranking;
import com.junsu.cyr.domain.rankings.RankingCategory;
import com.junsu.cyr.domain.rankings.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.ranking.RankingConditionRequest;
import com.junsu.cyr.model.ranking.RankingResponse;
import com.junsu.cyr.repository.RankingRepository;
import com.junsu.cyr.response.exception.code.RankingExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UserService userService;
    private final RankingCategoryService rankingCategoryService;

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
    public void deleteRankingByRankingCategory(RankingCategory rankingCategory) {
        rankingRepository.deleteAllByRankingCategory(rankingCategory);
    }

    public List<RankingResponse> getRanking(Type type, RankingConditionRequest condition, Integer userId) {
        User user = userService.getUserById(userId);

        if(type == null) {
            throw new BaseException(RankingExceptionCode.INVALID_TYPE);
        } else if(condition.getPeriod() == null) {
            throw new BaseException(RankingExceptionCode.INVALID_PERIOD);
        }

        RankingCategory rankingCategory = rankingCategoryService.getRankingCategoryByTypeAndPeriod(type, condition.getPeriod());

        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);
        List<Ranking> rankingResponses = rankingRepository.findALlByRankingCategory(rankingCategory, pageable);

        return rankingResponses.stream().map(RankingResponse::new).toList();
    }

    public List<RankingResponse> getSummaryRanking() {

        return null;
    }
}
