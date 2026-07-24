package com.junsu.cyr.controller.qna;

import com.junsu.cyr.model.qna.QuestionResponse;
import com.junsu.cyr.model.qna.QuestionUploadRequest;
import com.junsu.cyr.service.qna.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<String> uploadQuestion(@RequestBody QuestionUploadRequest request, @RequestAttribute Integer userId) {
        questionService.createQuestion(request, userId);
        return ResponseEntity.ok("success to upload question");
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAllQuestions(@RequestParam String status, @RequestParam String sort, @RequestParam String direction) {
        List<QuestionResponse> responses = questionService.getQuestions(status, sort, direction);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<String> closeQuestionStatus(@PathVariable Long questionId, @RequestAttribute Integer userId) {
        questionService.closeQuestionStatus(userId, questionId);
        return ResponseEntity.ok("success to update question status");
    }
}
