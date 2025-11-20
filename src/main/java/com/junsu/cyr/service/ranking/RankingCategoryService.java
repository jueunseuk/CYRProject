package com.junsu.cyr.service.ranking;

import com.junsu.cyr.domain.rankings.Period;
import com.junsu.cyr.domain.rankings.RankingCategory;
import com.junsu.cyr.domain.rankings.Type;
import com.junsu.cyr.repository.RankingCategoryRepository;
import com.junsu.cyr.response.exception.code.RankingCategoryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingCategoryService {

    private final RankingCategoryRepository rankingCategoryRepository;

    public RankingCategory getRankingCategoryByRankingCategoryId(Integer rankingCategoryId) {
        return rankingCategoryRepository.findById(rankingCategoryId)
                .orElseThrow(() -> new BaseException(RankingCategoryExceptionCode.NOT_FOUND_RANKING_CATEGORY));
    }

    public RankingCategory getRankingCategoryByTypeAndPeriod(Type type, Period period) {
        return rankingCategoryRepository.findByTypeAndPeriod(type, period);
    }
}
