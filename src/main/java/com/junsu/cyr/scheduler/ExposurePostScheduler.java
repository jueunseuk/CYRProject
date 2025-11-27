package com.junsu.cyr.scheduler;

import com.junsu.cyr.service.post.ExposurePostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExposurePostScheduler {

    private final ExposurePostService exposurePostService;

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void deleteBeforeAMonth() {
        log.info("[ExposurePostScheduler] {} => 24시간이 경과한 노출 게시글 제거", LocalDateTime.now());
        exposurePostService.concealingPosts(24L);
    }
}
