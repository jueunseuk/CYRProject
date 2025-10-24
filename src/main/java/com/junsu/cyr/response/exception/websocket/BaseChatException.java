package com.junsu.cyr.response.exception.websocket;

import lombok.Getter;

@Getter
public class BaseChatException extends RuntimeException {
    private final String code;

    public BaseChatException(String code, String message) {
        super(message);
        this.code = code;
    }
}
