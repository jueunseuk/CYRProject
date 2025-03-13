package com.junsu.cyr.response;

import lombok.Getter;

@Getter
public abstract class ApiResponse {
    private final Boolean isSuccess;
    private final String code;
    private final String message;

    protected ApiResponse(Boolean isSuccess, String code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
