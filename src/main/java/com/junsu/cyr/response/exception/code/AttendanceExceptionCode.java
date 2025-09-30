package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AttendanceExceptionCode implements ExceptionCode {
    ALREADY_ATTEND_USER("ATT_001", "오늘 이미 출석을 한 사용자입니다.", HttpStatus.CONFLICT),
    CONTENT_DOES_NOT_EXISTS("ATT_002", "내용이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_ATTENDANCE("ATT_003", "해당 출석 내용을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
