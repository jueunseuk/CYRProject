package com.junsu.cyr.controller.post;

import com.junsu.cyr.model.post.*;
import com.junsu.cyr.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPost(postId);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<PostListResponse>> getAllPosts(@ModelAttribute PostSearchConditionRequest searchConditionRequest) {
        Page<PostListResponse> postListResponses = postService.getAllPosts(searchConditionRequest);
        return ResponseEntity.ok(postListResponses);
    }

    @GetMapping("/popular")
    public ResponseEntity<Page<PostListResponse>> getPopularPosts(@ModelAttribute PostSearchConditionRequest searchConditionRequest) {
        Page<PostListResponse> postListResponses = postService.getPopularPosts(searchConditionRequest);
        return ResponseEntity.ok(postListResponses);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PostListResponse>> getPosts(@ModelAttribute PostSearchConditionRequest searchConditionRequest) {
        Page<PostListResponse> postListResponses = postService.getPosts(searchConditionRequest);
        return ResponseEntity.ok(postListResponses);
    }

    @PostMapping("")
    public ResponseEntity<PostUploadResponse> uploadPost(@RequestBody PostUploadRequest request, @RequestAttribute Integer userId) {
        PostUploadResponse postUploadResponse = postService.uploadPost(request, userId);
        return ResponseEntity.ok(postUploadResponse);
    }

}
