package com.junsu.cyr.controller.chat;

import com.junsu.cyr.model.chat.ChatRoomRequest;
import com.junsu.cyr.model.chat.ChatRoomResponse;
import com.junsu.cyr.service.chat.ChatService;
import com.junsu.cyr.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomResponse> getChatRoom(@PathVariable Long chatRoomId, @RequestAttribute Integer userId) {
        ChatRoomResponse chatRoomResponse = chatRoomService.getChatRoomInfo(chatRoomId, userId);
        return ResponseEntity.ok(chatRoomResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatRoomResponse>> getChatRoomList(@RequestAttribute Integer userId) {
        List<ChatRoomResponse> chatRoomResponses = chatRoomService.getChatRoomList(userId);
        return ResponseEntity.ok(chatRoomResponses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChatRoomResponse>> getOtherChatRoomList(@RequestAttribute Integer userId) {
        List<ChatRoomResponse> chatRoomResponses = chatRoomService.getOhterChatRoomList(userId);
        return ResponseEntity.ok(chatRoomResponses);
    }

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody ChatRoomRequest request, @RequestAttribute Integer userId) {
        ChatRoomResponse chatRoomResponse = chatService.createChatRoom(request, userId);
        return ResponseEntity.ok(chatRoomResponse);
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable Long chatRoomId, @RequestAttribute Integer userId) {
        chatService.deleteChatRoom(chatRoomId, userId);
        return ResponseEntity.ok("success to delete chat room");
    }
}
