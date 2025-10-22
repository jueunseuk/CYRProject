package com.junsu.cyr.repository;

import com.junsu.cyr.domain.statistics.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Statistic findFirstByOrderByCreatedAtDesc();
}
