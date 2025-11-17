package com.junsu.cyr.controller.achievement;

import com.junsu.cyr.model.achievement.AchievementLogConditionRequest;
import com.junsu.cyr.model.achievement.AchievementLogResponse;
import com.junsu.cyr.service.achievement.AchievementLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievement")
public class AchievementController {

    private final AchievementLogService achievementLogService;

    @GetMapping("/completed")
    public ResponseEntity<List<AchievementLogResponse>> getAllAchievementLogs(@ModelAttribute AchievementLogConditionRequest condition, @RequestAttribute Integer userId) {
        List<AchievementLogResponse> achievementLogResponses = achievementLogService.getAchievementLogList(condition, userId);
        return ResponseEntity.ok(achievementLogResponses);
    }
}
