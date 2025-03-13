package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {
    FAILED_TO_GENERATE_JWT("AUTH_001", "JWT 생성 도중 실패했습니다.", HttpStatus.UNAUTHORIZED),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
