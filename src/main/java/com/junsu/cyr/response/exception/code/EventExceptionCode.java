package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EventExceptionCode implements ExceptionCode {
    NOT_FOUND_EVENT("EVENT_001", "해당 이벤트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TOO_SHORT_TITLE("EVENT_002", "제목이 존재하지 않거나 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_CONTENT("EVENT_003", "본문이 존재하지 않거나 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    INVALID_EVENT_TYPE("EVENT_004", "이벤트의 타입이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    NEGATIVE_MAX_USER("EVENT_005", "이벤트의 최대 인원 수는 0 이하일 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_DEADLINE("EVENT_006", "종료 시각이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
