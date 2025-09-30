package com.junsu.cyr.controller.user;

import com.junsu.cyr.model.user.UserProfileResponse;
import com.junsu.cyr.model.user.UserSidebarResponse;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
