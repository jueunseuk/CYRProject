package com.junsu.cyr.flow.user.asset;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.service.glass.GlassRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrantUserGlassFlow {

    private final UserService userService;
    private final GlassRewardService glassRewardService;

    @ManagerOnly
    @Transactional
    public void grantUserGlass(Integer memberId, Integer amount, Integer userId) {
        userService.getUserById(userId);

        User member = userService.getUserById(memberId);
        glassRewardService.addGlass(member, 5, amount);
    }
}
