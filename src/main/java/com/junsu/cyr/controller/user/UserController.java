package com.junsu.cyr.controller.user;

import com.junsu.cyr.model.comment.CommentResponse;
import com.junsu.cyr.model.comment.CommentSearchConditionRequest;
import com.junsu.cyr.model.comment.UserCommentResponse;
import com.junsu.cyr.model.gallery.GalleryImageResponse;
import com.junsu.cyr.model.gallery.GallerySearchConditionRequest;
import com.junsu.cyr.model.post.PostListResponse;
import com.junsu.cyr.model.post.PostSearchConditionRequest;
import com.junsu.cyr.model.user.*;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.empathy.EmpathyService;
import com.junsu.cyr.service.gallery.GalleryService;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final GalleryService galleryService;
    private final EmpathyService empathyService;

    @GetMapping("/sidebar")
    public ResponseEntity<UserSidebarResponse> getUserProfile(@RequestAttribute Integer userId) {
        UserSidebarResponse userSidebarResponse = userService.getUserSidebar(userId);
        return ResponseEntity.ok(userSidebarResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(@RequestAttribute Integer userId) {
        UserProfileResponse userProfileResponse = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfileResponse);
    }

    @GetMapping("/{otherId}")
    public ResponseEntity<OtherProfileResponse> getOtherProfile(@PathVariable Integer otherId) {
        OtherProfileResponse otherProfileResponse = userService.getOtherProfile(otherId);
        return ResponseEntity.ok(otherProfileResponse);
    }

    @PatchMapping("/profile/info")
    public ResponseEntity<?> updateUserProfileInformation(@RequestBody UserProfileUpdateRequest request, @RequestAttribute Integer userId) {
        UserProfileUpdateRequest userProfileUpdateRequest = userService.updateUserInformation(request, userId);
        return ResponseEntity.ok(userProfileUpdateRequest);
    }

    @PatchMapping("/profile/image")
    public ResponseEntity<String> updateUserProfileImage(MultipartFile request, @RequestAttribute Integer userId) {
        String userProfileUrl = userService.updateUserProfileImage(request, userId);
        return ResponseEntity.ok(userProfileUrl);
    }

    @GetMapping("/{userId}/activity")
    public ResponseEntity<UserActivityResponse> getUserActivityData(@PathVariable Integer userId) {
        UserActivityResponse userActivityResponse = userService.getUserActivityData(userId);
        return ResponseEntity.ok(userActivityResponse);
    }

    @PatchMapping("/profile/refresh")
    public ResponseEntity<UserActivityResponse> refreshUserProfile(@RequestAttribute Integer userId) {
        UserActivityResponse userActivityResponse = userService.forceRefresh(userId);
        return ResponseEntity.ok(userActivityResponse);
    }

    @GetMapping("/{searchId}/posts")
    public ResponseEntity<Page<PostListResponse>> getWritePosts(@PathVariable Integer searchId, @ModelAttribute PostSearchConditionRequest condition, @RequestAttribute Integer userId) {
        Page<PostListResponse> postListResponses = postService.getPostsByUser(searchId, userId, condition);
        return ResponseEntity.ok(postListResponses);
    }

    @GetMapping("/{searchId}/comments")
    public ResponseEntity<Page<UserCommentResponse>> getWriteComments(@PathVariable Integer searchId, @ModelAttribute CommentSearchConditionRequest condition, @RequestAttribute Integer userId) {
        Page<UserCommentResponse> userCommentResponses = commentService.getCommentsByUser(searchId, userId, condition);
        return ResponseEntity.ok(userCommentResponses);
    }

    @GetMapping("/{searchId}/images")
    public ResponseEntity<Page<GalleryImageResponse>> getUploadImages(@PathVariable Integer searchId, @ModelAttribute GallerySearchConditionRequest condition) {
        Page<GalleryImageResponse> galleryImageResponses = galleryService.getImagesByUser(searchId, condition);
        return ResponseEntity.ok(galleryImageResponses);
    }

    @GetMapping("/{searchId}/empathized-post")
    public ResponseEntity<Page<PostListResponse>> getEmpathyPosts(@PathVariable Integer searchId, @ModelAttribute PostSearchConditionRequest condition) {
        Page<PostListResponse> postListResponses = empathyService.getEmpathizePostByUser(searchId, condition);
        return ResponseEntity.ok(postListResponses);
    }
}
