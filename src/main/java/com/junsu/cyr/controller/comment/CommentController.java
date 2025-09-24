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
    public ResponseEntity<List<CommentResponse>> getPostComments(@PathVariable Long postId) {
        List<CommentResponse> response = commentService.getPostComments(postId);
        return ResponseEntity.ok(response);
    }

}
