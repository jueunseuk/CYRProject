package com.junsu.cyr.controller.post;

import com.junsu.cyr.model.post.PostListResponse;
import com.junsu.cyr.model.post.PostResponse;
import com.junsu.cyr.model.post.PostSearchConditionRequest;
import com.junsu.cyr.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/{boardId}")
public class PostController {

    private final PostService postService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Integer boardId,
                                                @PathVariable Long postId) {
        PostResponse postResponse = postService.getPost(boardId, postId);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostListResponse>> getPosts(@PathVariable Integer boardId,
                                                           @ModelAttribute PostSearchConditionRequest searchConditionRequest) {
        Page<PostListResponse> postListResponses = postService.getPosts(boardId, searchConditionRequest);
        return ResponseEntity.ok(postListResponses);
    }

}
