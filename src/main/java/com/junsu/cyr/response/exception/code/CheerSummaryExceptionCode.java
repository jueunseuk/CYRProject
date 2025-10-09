package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CheerSummaryExceptionCode implements ExceptionCode {
    NOT_CREATED_CHEER_SUMMARY("CHEERS_001", "오늘 출석한 기록이 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_PERIOD("CHEERS_002", "응원 주기가 너무 짧습니다.", HttpStatus.BAD_REQUEST),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
