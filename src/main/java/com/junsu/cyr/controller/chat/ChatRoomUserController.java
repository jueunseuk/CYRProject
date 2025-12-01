package com.junsu.cyr.controller.chat;

import com.junsu.cyr.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/room/user")
public class ChatRoomUserController {

    private final ChatService chatService;

    @PostMapping("/{chatRoomId}")
    public ResponseEntity<String> joinChatRoom(@PathVariable("chatRoomId") Long chatRoomId, @RequestAttribute Integer userId) {
        chatService.firstJoinChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("success to join chat room");
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> exitChatRoom(@PathVariable("chatRoomId") Long chatRoomId, @RequestAttribute Integer userId) {
        chatService.deleteUserFromChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("success to remove user from chat room");
    }
}
