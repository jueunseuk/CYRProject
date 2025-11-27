package com.junsu.cyr.flow.user.asset;

import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.glass.GlassService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrantUserGlassFlow {

    private final UserService userService;
    private final GlassService glassService;

    @Transactional
    public void grantUserGlass(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateGlass(amount);

        Glass glass = glassService.getGlass(5);
        glassService.createGlassLog(glass, user, amount);
    }
}
