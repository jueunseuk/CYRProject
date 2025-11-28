package com.junsu.cyr.service.poll;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.PollLog;
import com.junsu.cyr.domain.polls.PollOption;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.poll.PollOptionCount;
import com.junsu.cyr.repository.PollLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollLogService {

    private final PollLogRepository pollLogRepository;

    @Transactional
    public void createPollLog(User user, Poll poll, PollOption pollOption) {
        PollLog pollLog = PollLog.builder()
                .user(user)
                .poll(poll)
                .pollOption(pollOption)
                .build();

        pollLogRepository.save(pollLog);
    }

    public Boolean checkPollLogByUserAndPoll(User user, Poll poll) {
        return pollLogRepository.existsByUserAndPoll(user, poll);
    }

    public List<PollOptionCount> getPollResult(Poll poll) {
        return pollLogRepository.countByPollOption(poll);
    }

    public PollLog getPollLogByUserAndPoll(User user, Poll poll) {
        return pollLogRepository.findByUserAndPoll(user, poll)
                .orElse(null);
    }
}
