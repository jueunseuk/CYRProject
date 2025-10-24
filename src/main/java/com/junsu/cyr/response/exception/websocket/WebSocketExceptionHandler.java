package com.junsu.cyr.response.exception.websocket;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class WebSocketExceptionHandler {
    @MessageExceptionHandler(BaseChatException.class)
    @SendToUser("/queue/errors")
    public ChatErrorResponse handleChatError(BaseChatException e) {
        return new ChatErrorResponse(e.getCode(), e.getMessage());
    }
}
