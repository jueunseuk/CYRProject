package com.junsu.cyr.controller.chat;


import com.junsu.cyr.model.chat.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class ChatController {

    @MessageMapping("/chat.send")
    @SendTo("/topic/room.123")
    public ChatMessage send(ChatMessage msg) {
        // DB 저장 등 비즈니스 로직 가능
        return msg;
    }
}
