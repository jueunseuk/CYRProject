package com.junsu.cyr.controller.sand;

import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.service.sand.SandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sand")
public class SandController {

    private final SandService sandService;

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDateResponse> getSandData(@PathVariable Integer userId) {
        UserAssetDateResponse experienceDataResponse = sandService.getAssetData(userId);
        return ResponseEntity.ok(experienceDataResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<GraphResponse>> getSandHistory(@PathVariable Integer userId) {
        List<GraphResponse> graphResponses = sandService.getSandHistory(userId);
        return ResponseEntity.ok(graphResponses);
    }
}
