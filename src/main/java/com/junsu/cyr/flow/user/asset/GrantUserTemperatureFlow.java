package com.junsu.cyr.flow.user.asset;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.service.temperature.TemperatureRewardService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrantUserTemperatureFlow {

    private final UserService userService;
    private final TemperatureRewardService temperatureRewardService;

    @ManagerOnly
    @Transactional
    public void grantUserTemperature(Integer memberId, Integer amount, Integer userId) {
        userService.getUserById(userId);

        User member = userService.getUserById(memberId);
        temperatureRewardService.addTemperature(member, 100, amount);
    }
}
