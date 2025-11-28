package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
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

    @Transactional
    public void grantUserWarning(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateWarnCnt(amount);

        sandRewardService.addSand(member, 16);
    }
}
