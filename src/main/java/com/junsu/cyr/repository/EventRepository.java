package com.junsu.cyr.repository;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.Status;
import com.junsu.cyr.domain.events.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByLocked(Boolean aFalse, Pageable pageable);

    Page<Event> findAllByLockedAndType(Boolean aFalse, Type type, Pageable pageable);

    Page<Event> findAllByLockedAndStatus(Boolean aFalse, Status status, Pageable pageable);

    Page<Event> findAllByLockedAndStatusAndType(Boolean aFalse, Status status, Type type, Pageable pageable);

    List<Event> findByStatusAndClosedAtBefore(Status status, LocalDateTime now);
}
