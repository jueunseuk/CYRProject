package com.junsu.cyr.repository;

import com.junsu.cyr.domain.rankings.Period;
import com.junsu.cyr.domain.rankings.RankingCategory;
import com.junsu.cyr.domain.rankings.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingCategoryRepository extends JpaRepository<RankingCategory, Integer> {
    RankingCategory findByTypeAndPeriod(Type type, Period period);
}
