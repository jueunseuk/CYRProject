package com.junsu.cyr.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserScheduler {

    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void generateHourlyStatistic() {
        log.info("[UserScheduler] {} => 탈퇴한 지 일주일이 지난 사용자 찾기", LocalDateTime.now());

        try {
            Integer size = userService.userCleaning();
            log.info("{} => 일주일이 지난 사용자 {}명 삭제 완료", LocalDateTime.now(), size);
        } catch (Exception e) {
            log.error("사용자 삭제 중 오류 발생", e);
        }
    }
}
