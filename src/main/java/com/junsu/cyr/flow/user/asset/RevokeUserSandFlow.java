package com.junsu.cyr.flow.user.asset;

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
public class RevokeUserSandFlow {

    private final UserService userService;
    private final SandRewardService sandRewardService;

    @Transactional
    public void revokeUserSand(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        sandRewardService.addSand(member, 18, amount);
    }
}
