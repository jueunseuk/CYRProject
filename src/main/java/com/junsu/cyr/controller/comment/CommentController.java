package com.junsu.cyr.controller.comment;

import com.junsu.cyr.model.comment.CommentRequest;
import com.junsu.cyr.model.comment.CommentResponse;
import com.junsu.cyr.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> uploadComment(@RequestBody CommentRequest request, @RequestAttribute Integer userId) {
        commentService.uploadComment(request, userId);
        return ResponseEntity.ok("success to upload comment");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getPostComments(@PathVariable Long postId, @RequestParam Boolean fixed) {
        List<CommentResponse> response = commentService.getPostComments(postId, fixed);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request, @RequestAttribute Integer userId) {
        commentService.updateComment(request, commentId, userId);
        return ResponseEntity.ok("success to update comment");
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestAttribute Integer userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("success to delete comment");
    }

    @PatchMapping("/{commentId}/fixed")
    public ResponseEntity<?> updateFixStatus(@PathVariable Long commentId, @RequestParam Boolean fixed, @RequestAttribute Integer userId) {
        commentService.updateFix(commentId, fixed, userId);
        return ResponseEntity.ok("success to update fix status");
    }
}
