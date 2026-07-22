package com.junsu.cyr.scheduler;

import com.junsu.cyr.domain.polls.Poll;
import com.junsu.cyr.domain.polls.Status;
import com.junsu.cyr.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PollScheduler {

    private final PollRepository pollRepository;

    @Scheduled(cron = "0 */10 * * * *")
    @Transactional
    public void closeExpiredPolls() {
        log.info("Start searching for expired votes");

        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        List<Poll> polls = pollRepository.findByStatusAndClosedAtBefore(Status.IN_PROGRESS, now);
        polls.forEach(poll -> poll.updateStatus(Status.CLOSED));

        if (polls.isEmpty()) {
            log.info("No vote closed this time round.");
        } else {
            log.info("{} polls closed", polls.size());
        }
    }
}
