package com.junsu.cyr.controller.cheer;

import com.junsu.cyr.service.cheer.CheerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheer")
public class CheerController {

    private final CheerService cheerService;

    @GetMapping("")
    public ResponseEntity<?> getTotalCheer() {
        Long totalCheer = cheerService.getTotalCheer();
        return ResponseEntity.ok(totalCheer);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCheer(@RequestAttribute Integer userId) {
        cheerService.updateCheer(userId);
        return ResponseEntity.ok("Success to update cheer");
    }

}
