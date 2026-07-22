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
        log.info("Start scanning expired events");

        LocalDateTime now = LocalDateTime.now().plusSeconds(10);
        List<Event> events = eventRepository.findByStatusAndClosedAtBefore(Status.ACTIVE, now);
        events.forEach(event -> event.updateStatus(Status.CLOSED));

        if (events.isEmpty()) {
            log.info("No event closed for this round.");
        } else {
            log.info("{} events closed", events.size());
        }
    }
}
