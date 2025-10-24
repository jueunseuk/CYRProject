package com.junsu.cyr.response.exception;

import com.junsu.cyr.response.ApiResponse;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.Getter;

@Getter
public class ExceptionResponse extends ApiResponse {
    public ExceptionResponse(String code, String message) {
        super(false, code, message);
    }

    public static ExceptionResponse of(BaseException baseException) {
        return new ExceptionResponse(baseException.getCode(), baseException.getMessage());
    }
}
