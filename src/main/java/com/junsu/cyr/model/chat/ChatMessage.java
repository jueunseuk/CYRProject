package com.junsu.cyr.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    private String content;
    private String sender;
}
