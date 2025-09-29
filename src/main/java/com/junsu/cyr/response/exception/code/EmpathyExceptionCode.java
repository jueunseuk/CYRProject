package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmpathyExceptionCode implements ExceptionCode {
    ALREADY_EMPATHIZE_POST("EMP_001", "이미 공감을 한 게시글입니다.", HttpStatus.BAD_REQUEST),
    NEVER_EMPATHIZE("EMP_002", "아직 공감하지 않은 게시글입니다.", HttpStatus.BAD_REQUEST),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
