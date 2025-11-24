package com.junsu.cyr.repository;

import com.junsu.cyr.domain.events.EventComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCommentRepository extends JpaRepository<EventComment, Long> {
}
