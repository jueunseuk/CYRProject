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
public class AdminOnlyAspect {

    private final UserService userService;

    @Before("@annotation(com.junsu.cyr.global.annotation.AdminOnly) && args(.., userId)")
    public void checkManager(JoinPoint joinPoint, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastAdmin(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_ADMIN);
        }
    }
}
