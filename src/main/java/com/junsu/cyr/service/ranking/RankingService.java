package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.rankings.Ranking;
import com.junsu.cyr.domain.rankings.RankingCategory;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CheerSummaryRepository;
import com.junsu.cyr.repository.RankingCategoryRepository;
import com.junsu.cyr.repository.RankingRepository;
import com.junsu.cyr.response.exception.code.RankingExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final CheerSummaryRepository cheerSummaryRepository;
    private final RankingCategoryRepository rankingCategoryRepository;
    private final RankingCategoryService rankingCategoryService;
    private final UserService userService;

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
}

