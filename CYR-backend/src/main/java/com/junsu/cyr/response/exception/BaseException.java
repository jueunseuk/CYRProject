package com.junsu.cyr.response.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BaseException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public BaseException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
        this.httpStatus = exceptionCode.getStatus();
    }
}
