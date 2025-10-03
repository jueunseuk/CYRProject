package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlassLogExceptionCode implements ExceptionCode {
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
