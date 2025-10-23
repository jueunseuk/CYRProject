package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatMessageExceptionCode implements ExceptionCode {
    NOT_FOUND_CHAT_ROOM("CM_001", "해당 메세지는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    TOO_SHORT_CONTENT("CM_002", "메세지의 내용이 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
