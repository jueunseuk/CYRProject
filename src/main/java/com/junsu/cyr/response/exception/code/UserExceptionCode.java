package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
    NOT_EXIST_USER("USER_001", "존재하지 않는 사용자입니다.", HttpStatus.BAD_REQUEST),
    DO_NOT_HAVE_PERMISSION("USER_002", "수행할 권한이 없습니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_INTRODUCTION("USER_003", "한 줄 소개의 내용이 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    REQUIRES_AT_LEAST_MANAGER("USER_004", "적어도 매니저의 권한이 필요합니다.", HttpStatus.FORBIDDEN),
    REQUIRES_AT_LEAST_ADMIN("USER_005", "적어도 어드민의 권한이 필요합니다.", HttpStatus.FORBIDDEN),
    WARNING_ALREADY_ZERO("USER_006", "이미 경고 횟수가 0입니다.", HttpStatus.BAD_REQUEST),
    INCORRECT_ROLE_CHANGE_REQUEST("USER_007", "옳지 않은 권한 변경 요청입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
