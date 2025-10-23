package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatRoomUserExceptionCode implements ExceptionCode {
    NOT_FOUND_CHAT_ROOM_USER("CRU_001", "채팅방에 입장할 권한이 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_JOIN_CHAT_ROOM("CRU_001", "이미 채팅방에 가입된 상태입니다.", HttpStatus.CONFLICT),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
