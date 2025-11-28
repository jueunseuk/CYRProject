package com.junsu.cyr.repository;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.EventComment;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCommentRepository extends JpaRepository<EventComment, Long> {
    List<EventComment> findAllByEvent(Event event);

    Boolean existsByUserAndEvent(User user, Event event);

    List<EventComment> findAllByEventOrderByCreatedAtAsc(Event event);
}
