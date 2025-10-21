package com.junsu.cyr.repository;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, Long> {

    List<PollOption> findAllByPoll(Poll poll);

    PollOption findByPollOptionId(Long pollOptionId);
}
