package com.junsu.cyr.service.poll;

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
public class PollSchedulerService {

    private final PollRepository pollRepository;

    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void closeExpiredPolls() {
        log.info("[PollScheduler] {} : 만료된 투표 검색 시작", LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        List<Poll> polls = pollRepository.findByStatusAndClosedAtBefore(Status.IN_PROGRESS, now);
        polls.forEach(poll -> poll.updateStatus(Status.CLOSED));

        if (polls.isEmpty()) {
            log.info("[PollScheduler] 이번 회차에 마감된 투표 없음.");
        } else {
            log.info("[PollScheduler] {} : {}개의 투표 마감 완료", LocalDateTime.now(), polls.size());
        }
    }
}
