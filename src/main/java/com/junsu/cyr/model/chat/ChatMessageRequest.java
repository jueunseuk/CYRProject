package com.junsu.cyr.model.chat;

import com.junsu.cyr.domain.chats.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageRequest {
    private Integer userId;
    private String content;
    private Type type;
}
