package com.junsu.cyr.controller.qna;

import com.junsu.cyr.model.qna.AnswerResponse;
import com.junsu.cyr.model.qna.AnswerUploadRequest;
import com.junsu.cyr.service.qna.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question/{questionId}/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<String> uploadAnswer(@RequestBody AnswerUploadRequest request, @RequestAttribute Integer userId, @PathVariable Long questionId) {
        answerService.createAnswer(questionId, userId, request);
        return ResponseEntity.ok("success to upload answer");
    }

    @GetMapping
    public ResponseEntity<List<AnswerResponse>> getAllAnswer(@RequestAttribute Integer userId, @PathVariable Long questionId) {
        List<AnswerResponse> responses = answerService.getAllAnswerByQuestion(userId, questionId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{answerId}/adopt")
    public ResponseEntity<String> adoptAnswer(@RequestAttribute Integer userId, @PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.adoptAnswer(userId, questionId, answerId);
        return ResponseEntity.ok("success to adopt answer");
    }
}
