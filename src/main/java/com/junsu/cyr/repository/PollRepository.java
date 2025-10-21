package com.junsu.cyr.repository;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {

    List<Poll> findAllByStatus(Status status);

    List<Poll> findByStatusAndClosedAtBefore(Status status, LocalDateTime now);
}
