package com.junsu.cyr.service.sand;

import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.sand.SandLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.SandLogRepository;
import com.junsu.cyr.repository.SandRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.SandExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SandService {

    private SandRepository sandRepository;
    private SandLogRepository sandLogRepository;

    public Sand getSand(Integer sandId) {
        return sandRepository.findById(sandId)
                .orElseThrow(() -> new BaseException(SandExceptionCode.NOT_FOUND_SAND));
    }

    public void createSandLog(Sand sand, User user) {
        SandLog sandLog = SandLog.builder()
                .sand(sand)
                .user(user)
                .after(user.getSand())
                .build();

        sandLogRepository.save(sandLog);
    }
}
