package com.junsu.cyr.controller.user;

import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/exp")
    public ResponseEntity<Long> getExp(@RequestAttribute Integer userId) {
        Long totalExp = userService.getUserExp(userId);
        return ResponseEntity.ok(totalExp);
    }
}
