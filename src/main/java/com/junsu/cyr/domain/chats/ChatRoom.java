package com.junsu.cyr.domain.chats;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long chatRoomId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_messaged_at")
    private LocalDateTime lastMessagedAt;

    @Column(name = "last_message")
    private String lastMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type lastMessageType;

    @Column(name = "member")
    private Long memberCount;

    @Column(name = "max_member")
    private Long maxMember;

    public void increaseMemberCount() {
        memberCount++;
    }

    public void decreaseMemberCount() {
        memberCount--;
        if(memberCount < 0) {
            this.memberCount = 0L;
        }
    }

    public void updateLastMessage(ChatMessage chatMessage) {
        this.lastMessage = chatMessage.getContent();
        this.lastMessagedAt = LocalDateTime.now();
        this.lastMessageType = chatMessage.getType();
    }
}
