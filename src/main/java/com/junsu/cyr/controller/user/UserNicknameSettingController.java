package com.junsu.cyr.controller.user;

import com.junsu.cyr.flow.user.profile.UpdateUserNicknameColorFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/setting/nickname")
public class UserNicknameSettingController {

    private final UpdateUserNicknameColorFlow updateUserNicknameColorFlow;

    @PostMapping
    public ResponseEntity<String> setUserNicknameColor(@RequestParam Integer shopItemId, @RequestAttribute Integer userId) {
        updateUserNicknameColorFlow.updateUserNicknameColor(shopItemId, userId);
        return ResponseEntity.ok("success to update user nickname color");
    }
}
