package com.junsu.cyr.response.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.junsu.cyr.response.ApiResponse;

public class SuccessResponse<T> extends ApiResponse {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final T result;

    public SuccessResponse(T result) {
        super(true, "200", "OK");
        this.result = result;
    }

    public static <T> SuccessResponse<T> success(T result) {
        return new SuccessResponse<T>(result);
    }

    public static <T> SuccessResponse<T> successWithOutResponse() {
        return new SuccessResponse<T>(null);
    }
}
