package com.junsu.cyr.repository;

import com.junsu.cyr.domain.rankings.Period;
import com.junsu.cyr.domain.rankings.Ranking;
import com.junsu.cyr.domain.rankings.RankingCategory;
import com.junsu.cyr.domain.rankings.Type;
import com.junsu.cyr.model.ranking.RankingResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    void deleteAllByRankingCategory(RankingCategory rankingCategory);

    List<Ranking> findALlByRankingCategory(RankingCategory rankingCategory, Pageable pageable);
}
