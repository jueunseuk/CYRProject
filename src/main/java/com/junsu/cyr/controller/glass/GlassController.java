package com.junsu.cyr.controller.glass;

import com.junsu.cyr.model.common.UserAssetDateResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.service.glass.GlassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/glass")
public class GlassController {

    private final GlassService glassService;

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDateResponse> getGlassData(@PathVariable Integer userId) {
        UserAssetDateResponse userAssetDateResponse = glassService.getAssetData(userId);
        return ResponseEntity.ok(userAssetDateResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<GraphResponse>> getGlassHistory(@PathVariable Integer userId) {
        List<GraphResponse> graphResponses = glassService.getGlassHistory(userId);
        return ResponseEntity.ok(graphResponses);
    }
}
