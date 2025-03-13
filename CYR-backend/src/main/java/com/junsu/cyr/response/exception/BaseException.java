package com.junsu.cyr.response.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BaseException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public BaseException(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
