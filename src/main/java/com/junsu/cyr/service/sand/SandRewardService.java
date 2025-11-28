package com.junsu.cyr.service.sand;

import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SandRewardService {

    private final SandService sandService;
    private final SandLogService sandLogService;

    @Transactional
    public void addSand(User user, Integer sandId) {
        Sand sand = sandService.getSand(sandId);
        user.updateSand(sand.getAmount());
        sandLogService.createSandLog(sand, sand.getAmount(), user);
    }

    @Transactional
    public void addSand(User user, Integer sandId, Integer amount) {
        Sand sand = sandService.getSand(sandId);
        user.updateSand(amount);
        sandLogService.createSandLog(sand, amount, user);
    }

    @Transactional
    public void addSand(User user, Sand sand, Integer amount) {
        user.updateSand(amount);
        sandLogService.createSandLog(sand, amount, user);
    }
}
