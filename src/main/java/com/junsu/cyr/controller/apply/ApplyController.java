package com.junsu.cyr.controller.apply;

import com.junsu.cyr.model.apply.ApplyConditionRequest;
import com.junsu.cyr.model.apply.ApplyResponse;
import com.junsu.cyr.model.apply.ApplyUploadRequest;
import com.junsu.cyr.service.apply.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

    private final ApplyService applyService;

    @GetMapping("/{applyId}")
    public ResponseEntity<ApplyResponse> getApply(@PathVariable Long applyId, @RequestAttribute Integer userId) {
        ApplyResponse applyResponse = applyService.getApply(applyId, userId);
        return ResponseEntity.ok(applyResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ApplyResponse>> getApplyList(@ModelAttribute ApplyConditionRequest condition, @RequestAttribute Integer userId) {
        Page<ApplyResponse> applyResponse = applyService.getApplyList(condition, userId);
        return ResponseEntity.ok(applyResponse);
    }

    @PostMapping
    public ResponseEntity<ApplyResponse> uploadApply(@RequestBody ApplyUploadRequest request, @RequestAttribute Integer userId) {
        ApplyResponse applyResponse = applyService.uploadApply(request, userId);
        return ResponseEntity.ok(applyResponse);
    }

    @PatchMapping("/{applyId}/confirm")
    public ResponseEntity<String> confirmApply(@PathVariable Long applyId, @RequestAttribute Integer userId) {
        applyService.confirmApply(applyId, userId);
        return ResponseEntity.ok("success to confirm apply");
    }
}
