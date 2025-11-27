package com.junsu.cyr.scheduler;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.Status;
import com.junsu.cyr.repository.EventRepository;
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
public class EventScheduler {

    private final EventRepository eventRepository;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void closeExpiredEvents() {
        log.info("[EventScheduler] {} : 만료된 이벤트 검색 시작", LocalDateTime.now());

        LocalDateTime now = LocalDateTime.now().plusSeconds(10);
        List<Event> events = eventRepository.findByStatusAndClosedAtBefore(Status.ACTIVE, now);
        events.forEach(event -> event.updateStatus(Status.CLOSED));

        if (events.isEmpty()) {
            log.info("[EventScheduler] 이번 회차에 마감된 이벤트 없음.");
        } else {
            log.info("[EventScheduler] {} : {}개의 이벤트 마감 완료", LocalDateTime.now(), events.size());
        }
    }
}
