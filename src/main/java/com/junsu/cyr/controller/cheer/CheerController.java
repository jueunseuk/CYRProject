package com.junsu.cyr.controller.cheer;

import com.junsu.cyr.service.service.CheerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheer")
public class CheerController {

    private final CheerService cheerService;

    @PatchMapping("/update")
    public ResponseEntity<?> updateCheer(@RequestAttribute Integer userId) {
        cheerService.updateCheer(userId);
        return ResponseEntity.ok("Success to update cheer");
    }

}
