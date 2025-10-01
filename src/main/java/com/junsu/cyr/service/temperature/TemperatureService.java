package com.junsu.cyr.service.temperature;

import com.junsu.cyr.domain.temperature.Temperature;
import com.junsu.cyr.domain.temperature.TemperatureLog;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.TemperatureLogRepository;
import com.junsu.cyr.repository.TemperatureRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.TemperatureExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    public Temperature getTemperature(Integer temperatureId) {
        return temperatureRepository.findById(temperatureId)
                .orElseThrow(() -> new BaseException(TemperatureExceptionCode.NOT_FOUND_TEMPERATURE));
    }

    public void createTemperatureLog(User user, Temperature temperature) {
        TemperatureLog temperatureLog = TemperatureLog.builder()
                .user(user)
                .temperature(temperature)
                .after(user.getTemperature())
                .build();

        temperatureLogRepository.save(temperatureLog);
    }
}
