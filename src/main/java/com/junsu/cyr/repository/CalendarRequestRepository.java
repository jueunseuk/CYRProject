package com.junsu.cyr.repository;

import com.junsu.cyr.domain.calendar.CalendarRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRequestRepository extends JpaRepository<CalendarRequest, Integer> {
    @Query("select cr from CalendarRequest as cr where cr.status = false")
    List<CalendarRequest> findScheduleRequestWithBefore();

    @Query("select cr from CalendarRequest as cr where cr.calendarRequestId = :calendarRequestId")
    Optional<CalendarRequest> findCalendarRequestById(Long calendarRequestId);
}
