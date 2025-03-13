package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmailExceptionCode implements ExceptionCode {
    FAILED_TO_GENERATE_CODE("MAIL_001", "확인 코드를 생성하는데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED_TO_SEND_CODE("MAIL_002", "확인 코드를 전송하는데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNMATCHED_AUTHENTICATION_CODE("MAIL_003", "인증 코드와 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
