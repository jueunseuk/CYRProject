package com.junsu.cyr.controller.sand;

import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.service.sand.SandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sand")
public class SandController {

    private final SandService sandService;

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDataResponse> getSandData(@PathVariable Integer userId) {
        UserAssetDataResponse userAssetDataResponse = sandService.getAssetData(userId);
        return ResponseEntity.ok(userAssetDataResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<GraphResponse>> getSandHistory(@PathVariable Integer userId) {
        List<GraphResponse> graphResponses = sandService.getSandHistory(userId);
        return ResponseEntity.ok(graphResponses);
    }
}
