package com.junsu.cyr.flow.user.asset;

import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.temperature.TemperatureService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RevokeUserTemperatureFlow {

    private final UserService userService;
    private final TemperatureService temperatureService;

    @Transactional
    public void revokeUserTemperature(Integer memberId, Integer amount, Integer userId) {
        User user = userService.getUserById(userId);
        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        User member = userService.getUserById(memberId);
        member.updateTemperature(amount);

        Temperature temperature = temperatureService.getTemperature(101);
        temperatureService.createTemperatureLog(user, temperature);
    }
}
