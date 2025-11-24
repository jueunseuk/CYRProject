package com.junsu.cyr.repository;

import com.junsu.cyr.domain.rankings.Ranking;
import com.junsu.cyr.domain.rankings.RankingCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    void deleteAllByRankingCategory(RankingCategory rankingCategory);

    List<Ranking> findAllByRankingCategory(RankingCategory rankingCategory, Pageable pageable);

    @Query("select r.user.userId from Ranking r")
    List<Integer> findUser_UserId();
}
