package com.junsu.cyr.service.glass;

import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GlassRewardService {

    private final GlassService glassService;

    @Transactional
    public void addGlass(User user, Integer glassId) {
        Glass glass = glassService.getGlass(glassId);
        user.updateGlass(glass.getAmount());
        glassService.createGlassLog(glass, user, glass.getAmount());
    }

    @Transactional
    public void addGlass(User user, Integer glassId, Integer amount) {
        Glass glass = glassService.getGlass(glassId);
        user.updateGlass(amount);
        glassService.createGlassLog(glass, user, amount);
    }

    @Transactional
    public void addGlass(User user, Glass glass, Integer amount) {
        user.updateGlass(amount);
        glassService.createGlassLog(glass, user, amount);
    }
}
