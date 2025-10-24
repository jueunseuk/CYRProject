package com.junsu.cyr.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageConditionRequest {
    private Integer page = 0;
    private Integer size = 20;
    private String sort = "createdAt";
    private String direction = "DESC";
}
