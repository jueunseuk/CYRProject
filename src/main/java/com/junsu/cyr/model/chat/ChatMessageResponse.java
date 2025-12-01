package com.junsu.cyr.model.chat;

import com.junsu.cyr.domain.chats.ChatMessage;
import com.junsu.cyr.domain.chats.Type;
import com.junsu.cyr.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatMessageResponse {
    private Integer userId;
    private String nickname;
    private String color;
    private String profileUrl;
    private String content;
    private Type type;
    private LocalDateTime createdAt;

    public ChatMessageResponse(ChatMessage chatMessage, String color) {
        this.content = chatMessage.getContent();
        this.type = chatMessage.getType();
        this.createdAt = chatMessage.getCreatedAt();
        this.color = color;
        if(chatMessage.getUser() != null) {
            User user = chatMessage.getUser();
            this.userId = user.getUserId();
            this.nickname = user.getNickname();
            this.profileUrl = user.getProfileUrl();
        }
    }
}
