package com.junsu.cyr.service.cheer;

import com.junsu.cyr.domain.cheers.CheerLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.CheerLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheerLogService {

    private final CheerLogRepository cheerLogRepository;

    @Transactional
    public void createCheerLog(User user) {
        CheerLog cheerLog = CheerLog.builder()
                .user(user)
                .build();

        cheerLogRepository.save(cheerLog);
    }
}
