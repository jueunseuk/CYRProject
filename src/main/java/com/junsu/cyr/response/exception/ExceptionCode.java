package com.junsu.cyr.response.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
