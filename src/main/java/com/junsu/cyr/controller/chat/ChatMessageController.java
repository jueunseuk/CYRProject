package com.junsu.cyr.controller.chat;

import com.junsu.cyr.model.chat.ChatMessageConditionRequest;
import com.junsu.cyr.model.chat.ChatMessageResponse;
import com.junsu.cyr.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/message")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessageList(@ModelAttribute ChatMessageConditionRequest request, @PathVariable Long chatRoomId, @RequestAttribute Integer userId) {
        List<ChatMessageResponse> chatMessageResponses = chatMessageService.getMessageList(request, chatRoomId, userId);
        return ResponseEntity.ok(chatMessageResponses);
    }
}
