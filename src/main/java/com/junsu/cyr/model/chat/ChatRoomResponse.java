package com.junsu.cyr.model.chat;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.chats.Type;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomResponse {
    private Long chatRoomId;
    private String name;
    private LocalDateTime createdAt;
    private Long totalMember;
    private LocalDateTime lastUpdatedAt;
    private String lastContent;
    private Type lastMessgaetType;

    public ChatRoomResponse(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.name = chatRoom.getName();
        this.createdAt = chatRoom.getCreatedAt();
        this.totalMember = chatRoom.getMemberCount();
        this.lastUpdatedAt = chatRoom.getLastMessagedAt();
        this.lastContent = chatRoom.getLastMessage();
        this.lastMessgaetType = chatRoom.getLastMessageType();
    }
}
