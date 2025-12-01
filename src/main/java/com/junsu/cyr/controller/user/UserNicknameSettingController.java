package com.junsu.cyr.controller.user;

import com.junsu.cyr.flow.user.profile.UpdateUserNicknameColorFlow;
import com.junsu.cyr.model.shop.ShopItemResponse;
import com.junsu.cyr.model.userNickname.UserNicknameRequest;
import com.junsu.cyr.service.user.UserNicknameSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/setting/nickname")
public class UserNicknameSettingController {

    private final UpdateUserNicknameColorFlow updateUserNicknameColorFlow;
    private final UserNicknameSettingService userNicknameSettingService;

    @PostMapping
    public ResponseEntity<String> setUserNicknameColor(@RequestBody UserNicknameRequest request, @RequestAttribute Integer userId) {
        updateUserNicknameColorFlow.updateUserNicknameColor(request.getShopItemId(), userId);
        return ResponseEntity.ok("success to update user nickname color");
    }

    @GetMapping
    public ResponseEntity<ShopItemResponse> getUserNicknameColor(@RequestAttribute Integer userId) {
        ShopItemResponse shopItemResponse = userNicknameSettingService.getUserNicknameSetting(userId);
        return ResponseEntity.ok(shopItemResponse);
    }
}
