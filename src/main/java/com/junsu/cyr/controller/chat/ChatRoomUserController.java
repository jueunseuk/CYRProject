package com.junsu.cyr.controller.chat;

import com.junsu.cyr.flow.chat.ExitChatRoomFlow;
import com.junsu.cyr.flow.chat.JoinChatRoomFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/room/user")
public class ChatRoomUserController {

    private final JoinChatRoomFlow joinChatRoomFlow;
    private final ExitChatRoomFlow exitChatRoomFlow;

    @PostMapping("/{chatRoomId}")
    public ResponseEntity<String> joinChatRoom(@PathVariable("chatRoomId") Long chatRoomId, @RequestAttribute Integer userId) {
        joinChatRoomFlow.joinChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("success to join chat room");
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> exitChatRoom(@PathVariable("chatRoomId") Long chatRoomId, @RequestAttribute Integer userId) {
        exitChatRoomFlow.exitChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("success to remove user from chat room");
    }
}
