package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatRoomUserExceptionCode implements ExceptionCode {
    NOT_FOUND_CHAT_ROOM_USER("CRU_001", "채팅방에 접근할 권한이 없습니다.", HttpStatus.NOT_FOUND),
    ALREADY_JOIN_CHAT_ROOM("CRU_002", "이미 채팅방에 가입된 상태입니다.", HttpStatus.CONFLICT),
    CHAT_ROOM_CAPACITY_EXCEEDED("CUR_003", "수용 가능한 인원을 초과했습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
