package com.junsu.cyr.controller.chat;

import com.junsu.cyr.model.chat.ChatMessageRequest;
import com.junsu.cyr.model.chat.ChatMessageResponse;
import com.junsu.cyr.service.chat.ChatWebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatWebSocketService chatWebSocketService;

    @MessageMapping("/chat.send.{chatRoomId}")
    @SendTo("/topic/chatroom.{chatRoomId}")
    public ChatMessageResponse send(@DestinationVariable Long chatRoomId, ChatMessageRequest request) {
        return chatWebSocketService.saveAndBroadcast(chatRoomId, request);
    }

    @MessageMapping("/chat.enter.{chatRoomId}")
    @SendTo("/topic/chatroom.{chatRoomId}")
    public ChatMessageResponse enterRoom(@DestinationVariable Long chatRoomId, ChatMessageRequest request) {
        return chatWebSocketService.enterRoom(chatRoomId, request);
    }

    @MessageMapping("/chat.leave.{chatRoomId}")
    @SendTo("/topic/chatroom.{chatRoomId}")
    public ChatMessageResponse leaveRoom(@DestinationVariable Long chatRoomId, ChatMessageRequest request) {
        return chatWebSocketService.leaveRoom(chatRoomId, request);
    }

    @MessageMapping("/chat.join.{chatRoomId}")
    @SendTo("/topic/chatroom.{chatRoomId}")
    public ChatMessageResponse join(@DestinationVariable Long chatRoomId, ChatMessageRequest request) {
        return chatWebSocketService.joinRoom(chatRoomId, request.getUserId());
    }

    @MessageMapping("/chat.exit.{chatRoomId}")
    @SendTo("/topic/chatroom.{chatRoomId}")
    public ChatMessageResponse exit(@DestinationVariable Long chatRoomId, ChatMessageRequest request) {
        return chatWebSocketService.exitRoom(chatRoomId, request.getUserId());
    }
}
