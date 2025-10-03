package com.junsu.cyr.controller.experience;

import com.junsu.cyr.model.common.ExperienceHistoryResponse;
import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.service.experience.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.Graph;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<GraphResponse>> getExperienceHistory(@PathVariable Integer userId) {
        List<GraphResponse> graphResponses = experienceService.getExperienceHistory(userId);
        return ResponseEntity.ok(graphResponses);
    }
}
