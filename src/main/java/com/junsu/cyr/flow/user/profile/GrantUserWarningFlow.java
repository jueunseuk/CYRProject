package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrantUserWarningFlow {

    private final UserService userService;
    private final SandRewardService sandRewardService;

    @ManagerOnly
    @Transactional
    public void grantUserWarning(Integer memberId, Integer amount, Integer userId) {
        userService.getUserById(userId);

        User member = userService.getUserById(memberId);
        member.updateWarnCnt(amount);

        sandRewardService.addSand(member, 16);
    }
}
