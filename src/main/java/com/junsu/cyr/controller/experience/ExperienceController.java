package com.junsu.cyr.controller.experience;

import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.service.experience.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDataResponse> getExperienceData(@PathVariable Integer userId) {
        UserAssetDataResponse userAssetDataResponse = experienceService.getAssetData(userId);
        return ResponseEntity.ok(userAssetDataResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<GraphResponse>> getExperienceHistory(@PathVariable Integer userId) {
        List<GraphResponse> graphResponses = experienceService.getExperienceHistory(userId);
        return ResponseEntity.ok(graphResponses);
    }
}
