package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {
    INVALID_PASSWORD_VALUE("AUTH_001", "유효하지 않은 패스워드입니다.", HttpStatus.BAD_REQUEST),
    INVALID_ACCESS_TOKEN("AUTH_002", "유효하지 않은 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("AUTH_003", "유효하지 않은 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED),
    NO_CORRESPONDING_REFRESH_TOKEN("AUTH_004", "일치하는 리프레시 토큰을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
