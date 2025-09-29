package com.junsu.cyr.controller.empathy;

import com.junsu.cyr.model.comment.CommentRequest;
import com.junsu.cyr.model.empathy.EmpathyResponse;
import com.junsu.cyr.service.empathy.EmpathyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/empathy")
public class EmpathyController {

    private final EmpathyService empathyService;

    @PostMapping("/{postId}")
    public ResponseEntity<EmpathyResponse> createEmpathy(@PathVariable Long postId, @RequestAttribute Integer userId) {
        EmpathyResponse empathyResponse = empathyService.createEmpathy(postId, userId);
        return ResponseEntity.ok(empathyResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteEmpathy(@PathVariable Long postId, @RequestAttribute Integer userId) {
        empathyService.deleteEmpathy(postId, userId);
        return ResponseEntity.ok("success to delete empathy");
    }
}
