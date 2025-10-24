package com.junsu.cyr.response.exception.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatErrorResponse {
    private final boolean success = false;
    private final String code;
    private final String message;
}
