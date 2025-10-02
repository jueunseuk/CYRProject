package com.junsu.cyr.controller.temperature;

import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.service.temperature.TemperatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/temperature")
public class TemperatureController {

    private final TemperatureService temperatureService;

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDateResponse> getTemperatureData(@PathVariable Integer userId) {
        UserAssetDateResponse userAssetDateResponse = temperatureService.getAssetData(userId);
        return ResponseEntity.ok(userAssetDateResponse);
    }
}
