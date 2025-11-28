package com.junsu.cyr.service.temperature;

import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemperatureRewardService {

    private final TemperatureService temperatureService;

    @Transactional
    public void addTemperature(User user, Integer temperatureId) {
        Temperature temperature = temperatureService.getTemperature(temperatureId);
        user.updateTemperature(temperature.getAmount());
        temperatureService.createTemperatureLog(user, temperature, temperature.getAmount());
    }

    @Transactional
    public void addTemperature(User user, Integer temperatureId, Integer amount) {
        Temperature temperature = temperatureService.getTemperature(temperatureId);
        user.updateTemperature(temperature.getAmount());
        temperatureService.createTemperatureLog(user, temperature, amount);
    }
}
