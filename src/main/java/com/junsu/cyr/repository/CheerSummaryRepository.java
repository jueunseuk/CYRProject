package com.junsu.cyr.repository;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.cheers.CheerSummaryId;
import com.junsu.cyr.repository.projection.TotalCheerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheerSummaryRepository extends JpaRepository<CheerSummary, CheerSummaryId> {
    @Query("select sum(cs.count) from CheerSummary cs")
    Long sumTotalCheers();

    @Query("select sum(cs.count) from CheerSummary cs where cs.cheerSummaryId.date = :date")
    Long sumByCheerSummaryId_Date(LocalDate date);

    Optional<CheerSummary> findByCheerSummaryId(CheerSummaryId cheerSummaryId);

    List<CheerSummary> findAllByCheerSummaryId_UserIdAndCheerSummaryId_DateBetween(Integer userId, LocalDate start, LocalDate end);

    List<CheerSummary> findTop10ByCheerSummaryId_DateBetweenOrderByCountDesc(LocalDate start, LocalDate localDate);

    @Query("select cs.cheerSummaryId.userId as userId, sum(cs.count) as sum from CheerSummary cs group by cs.cheerSummaryId.userId order by sum(cs.count) desc")
    List<TotalCheerProjection> findTotalCheerRanking();
}
