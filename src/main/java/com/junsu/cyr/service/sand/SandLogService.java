package com.junsu.cyr.service.sand;

import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.sand.SandLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.SandLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SandLogService {

    private final SandLogRepository sandLogRepository;

    @Transactional
    public void createSandLog(Sand sand, Integer delta, User user) {
        SandLog sandLog = SandLog.builder()
                .sand(sand)
                .user(user)
                .after(user.getSand())
                .delta(delta)
                .build();

        sandLogRepository.save(sandLog);
    }
}
