package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplyExceptionCode implements ExceptionCode {
    NOT_FOUND_APPLY("APPLY_001", "해당 운영자 신청을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    ALREADY_MANAGER_OR_ADMIN("APPLY_002", "이미 운영자 이상의 권한을 가지고 있습니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST_VALUE("APPLY_003", "운영자 신청 요청 값이 잘못 되었습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_CONFIRMED_APPLY("APPLY_004", "이미 확인된 신청 내용입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
