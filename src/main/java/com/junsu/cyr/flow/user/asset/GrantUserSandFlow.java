package com.junsu.cyr.flow.user.asset;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrantUserSandFlow {

    private final UserService userService;
    private final SandRewardService sandRewardService;

    @ManagerOnly
    @Transactional
    public void grantUserSand(Integer memberId, Integer amount, Integer userId) {
        userService.getUserById(userId);

        User member = userService.getUserById(memberId);
        sandRewardService.addSand(member, 18, amount);
    }
}
