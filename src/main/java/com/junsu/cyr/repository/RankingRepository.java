package com.junsu.cyr.repository;

import com.junsu.cyr.domain.rankings.Ranking;
import com.junsu.cyr.domain.rankings.RankingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    void deleteAllByRankingCategory(RankingCategory rankingCategory);
}
