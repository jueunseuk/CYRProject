package com.junsu.cyr.controller.post;

import com.junsu.cyr.model.post.PostListResponse;
import com.junsu.cyr.service.post.ExposurePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/exposed")
public class ExposurePostController {

    private final ExposurePostService exposurePostService;

    @GetMapping
    public ResponseEntity<List<PostListResponse>> getExposedPosts(@RequestAttribute Integer userId) {
        List<PostListResponse> postListResponses = exposurePostService.getExposedPosts(userId);
        return ResponseEntity.ok(postListResponses);
    }
}
