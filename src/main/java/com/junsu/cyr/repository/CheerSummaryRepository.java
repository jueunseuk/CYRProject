package com.junsu.cyr.repository;

import com.junsu.cyr.domain.cheers.CheerSummary;
import com.junsu.cyr.domain.cheers.CheerSummaryId;
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

    Optional<CheerSummary> findByCheerSummaryId(CheerSummaryId cheerSummaryId);

    List<CheerSummary> findAllByCheerSummaryId_UserIdAndCheerSummaryId_DateBetween(Integer userId, LocalDate start, LocalDate end);

    List<CheerSummary> findAllByCheerSummaryId_UserId(Integer userId);
}
