package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CalendarExceptionCode implements ExceptionCode {
    DO_NOT_HAVE_PERMISSION_TO_PROCESS("CAL_001", "요청을 처리할 권한이 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_CALENDAR_REQUEST_ID("CAL_002", "존재하지 않는 스케줄 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_PERSON_THE_PARTY_YOU_REQUESTED("CAL_003", "스케줄을 요청한 당사자가 아닙니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_CALENDAR_ID("CAL_004", "존재하지 않는 스케줄입니다.", HttpStatus.BAD_REQUEST),
    INVALID_VALUE_INJECTION("CAL_005", "올바르지 않은 값 주입 시도입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
