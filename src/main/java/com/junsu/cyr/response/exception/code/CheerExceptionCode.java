package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CheerExceptionCode implements ExceptionCode {
    NOT_FOUND_CHEER("CHEER_001", "해당 응원 기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_REQUEST_PERIOD("CHEER_002", "응원 주기가 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
