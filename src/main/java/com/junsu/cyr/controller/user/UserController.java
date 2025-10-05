package com.junsu.cyr.controller.user;

import com.junsu.cyr.model.user.OtherProfileResponse;
import com.junsu.cyr.model.user.UserProfileResponse;
import com.junsu.cyr.model.user.UserProfileUpdateRequest;
import com.junsu.cyr.model.user.UserSidebarResponse;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

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
}
