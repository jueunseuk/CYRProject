package com.junsu.cyr.flow.user.asset;

import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.flow.user.achievement.UnlockAchievementFlow;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.repository.GlassLogRepository;
import com.junsu.cyr.response.exception.code.GlassExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.glass.GlassService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConvertGlassFlow {

    private final UserService userService;
    private final GlassService glassService;
    private final GlassLogRepository glassLogRepository;
    private final UnlockAchievementFlow unlockAchievementFlow;

    @ManagerOnly
    @Transactional
    public void convertGlass(Integer userId) {
        User user = userService.getUserById(userId);

        if(user.getSand() < 100) {
            throw new BaseException(GlassExceptionCode.NOT_ENOUGH_SAND);
        } else if(user.getTemperature() != 1800) {
            throw new BaseException(GlassExceptionCode.NOT_ENOUGH_TEMPERATURE);
        }

        Glass glass = glassService.getGlass(1);

        user.convertGlass(glass.getAmount());

        Long cnt = glassLogRepository.countByUserAndGlass(user, glass);
        unlockAchievementFlow.unlockAchievement(user, Type.CRAFTSHOP, Scope.TOTAL, cnt);

        glassService.createGlassLog(glass, user, 1);
    }
}
