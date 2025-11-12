package com.junsu.cyr.controller.user;

import com.junsu.cyr.service.user.UserBoardSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/setting/board")
public class UserBoardSettingController {

    private final UserBoardSettingService userBoardSettingService;

    @GetMapping
    public ResponseEntity<List<Integer>> getMyFavoriteBoard(@RequestAttribute Integer userId) {
        List<Integer> response = userBoardSettingService.getUserFavoriteBoard(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<String> createUserBookmark(@PathVariable Integer boardId, @RequestAttribute Integer userId) {
        userBoardSettingService.updateFavoriteBoard(boardId, userId);
        return ResponseEntity.ok("success to add user bookmark");
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteUserBookmark(@PathVariable Integer boardId, @RequestAttribute Integer userId) {
        userBoardSettingService.updateFavoriteBoard(boardId, userId);
        return ResponseEntity.ok("success to delete user bookmark");
    }
}
