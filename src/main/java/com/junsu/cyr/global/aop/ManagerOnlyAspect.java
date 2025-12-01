
package com.junsu.cyr.global.aop;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ManagerOnlyAspect {

    private final UserService userService;

    @Before("@annotation(com.junsu.cyr.global.annotation.ManagerOnly) && args(.., userId)")
    public void checkManager(JoinPoint joinPoint, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }
    }
}
