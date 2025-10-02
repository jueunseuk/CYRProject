package com.junsu.cyr.controller.experience;

import com.junsu.cyr.model.common.ExperienceHistoryResponse;
import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.service.experience.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDateResponse> getExperienceData(@PathVariable Integer userId) {
        UserAssetDateResponse userAssetDateResponse = experienceService.getAssetData(userId);
        return ResponseEntity.ok(userAssetDateResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<ExperienceHistoryResponse> getExperienceHistory(@PathVariable Integer userId) {
        ExperienceHistoryResponse experienceHistoryResponse = experienceService.getExperienceHistory(userId);
        return ResponseEntity.ok(experienceHistoryResponse);
    }
}
