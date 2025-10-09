package com.junsu.cyr.controller.cheer;

import com.junsu.cyr.model.common.UserAssetDataResponse;
import com.junsu.cyr.model.user.GraphResponse;
import com.junsu.cyr.service.cheer.CheerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheer")
public class CheerController {

    private final CheerService cheerService;

    @GetMapping("/total")
    public ResponseEntity<?> getTotalCheer() {
        Long totalCheer = cheerService.getTotalCheer();
        return ResponseEntity.ok(totalCheer);
    }

    @PostMapping("")
    public ResponseEntity<Long> updateCheer(@RequestAttribute Integer userId) {
        Long response = cheerService.createCheer(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data/{userId}")
    public ResponseEntity<UserAssetDataResponse> getCheerData(@PathVariable Integer userId) {
        UserAssetDataResponse userAssetDataResponse = cheerService.getAssetData(userId);
        return ResponseEntity.ok(userAssetDataResponse);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<GraphResponse>> getCheerHistory(@PathVariable Integer userId) {
        List<GraphResponse> graphResponses = cheerService.getCheerHistory(userId);
        return ResponseEntity.ok(graphResponses);
    }
}
