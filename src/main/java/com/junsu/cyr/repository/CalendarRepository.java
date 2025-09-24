package com.junsu.cyr.repository;

import com.junsu.cyr.domain.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByDateBetween(LocalDate start, LocalDate end);

    @Query("select c from Calendar as c where c.date between :start and :end order by c.date desc")
    List<Calendar> findTop5ByOrder(LocalDate start, LocalDate end);
}
