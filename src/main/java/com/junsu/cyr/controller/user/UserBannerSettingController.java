package com.junsu.cyr.controller.user;

import com.junsu.cyr.model.userBannerSetting.UserBannerResponse;
import com.junsu.cyr.model.userBannerSetting.UserBannerSettingConditionRequest;
import com.junsu.cyr.model.userBannerSetting.UserBannerUpdateRequest;
import com.junsu.cyr.service.user.UserBannerSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/setting/banner")
public class UserBannerSettingController {

    private final UserBannerSettingService userBannerSettingService;

    @GetMapping
    public ResponseEntity<List<UserBannerResponse>> getUserBannerList(@ModelAttribute UserBannerSettingConditionRequest condition, @RequestAttribute Integer userId) {
        List<UserBannerResponse> userBannerResponses = userBannerSettingService.getUserBannerList(condition, userId);
        return ResponseEntity.ok(userBannerResponses);
    }

    @PatchMapping
    public ResponseEntity<String> updateUserBannerSequence(@RequestBody UserBannerUpdateRequest request, @RequestAttribute Integer userId) {
        userBannerSettingService.updateUserBannerSequence(request, userId);
        return ResponseEntity.ok("success to update user banner sequence");
    }
}
