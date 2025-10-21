package com.junsu.cyr.repository;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.PollOptionCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollLogRepository extends JpaRepository<PollLog, Long> {

    Boolean existsByUserAndPoll(User user, Poll poll);

    @Query("select pl.pollOption.pollOptionId, pl.pollOption.content, count(pl) from PollLog as pl where pl.poll = :poll group by pl.pollOption.pollOptionId")
    List<PollOptionCount> countByPollOption(Poll poll);

    Optional<PollLog> findByUserAndPoll(User user, Poll poll);
}
