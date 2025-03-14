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
    NO_CORRESPONDING_EMAIL_FOUND("MAIL_004", "일치하는 이메일을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    EMAIL_AUTHENTICATION_TIMEOUT("MAIL_005", "이메일 인증 시간을 초과했습니다.", HttpStatus.UNAUTHORIZED),
    ALREADY_EXIST_EMAIL("MAIL_006", "이미 존재하는 사용자의 이메일입니다.", HttpStatus.CONFLICT),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
