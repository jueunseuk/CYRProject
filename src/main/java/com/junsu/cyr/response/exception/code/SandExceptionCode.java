package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SandExceptionCode implements ExceptionCode {
    NOT_FOUND_SAND("SAND_001", "해당 모래알 종류는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
