package com.junsu.cyr.controller.glass;

import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.glass.GlassLogRequest;
import com.junsu.cyr.model.glass.GlassLogResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.service.glass.GlassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/glass")
public class GlassController {

    private final GlassService glassService;

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDataResponse> getGlassData(@PathVariable Integer userId) {
        UserAssetDataResponse userAssetDataResponse = glassService.getAssetData(userId);
        return ResponseEntity.ok(userAssetDataResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<GraphResponse>> getGlassHistory(@PathVariable Integer userId) {
        List<GraphResponse> graphResponses = glassService.getGlassHistory(userId);
        return ResponseEntity.ok(graphResponses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GlassLogResponse>> getAllGlassLog(@ModelAttribute GlassLogRequest condition) {
        List<GlassLogResponse> glassLogResponses = glassService.getGlassLogs(condition);
        return ResponseEntity.ok(glassLogResponses);
    }
}
