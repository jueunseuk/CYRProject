package com.junsu.cyr.flow.user.asset;

import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.sand.SandService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrantUserSandFlow {

    private final UserService userService;
    private final SandService sandService;

    @Transactional
    public void grantUserSand(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateSand(amount);

        Sand sand = sandService.getSand(18);
        sandService.createSandLog(sand, amount, user);
    }
}
