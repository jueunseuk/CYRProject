package com.junsu.cyr.repository;

import com.junsu.cyr.domain.calendar.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findByDateBetween(LocalDate start, LocalDate end);
}
