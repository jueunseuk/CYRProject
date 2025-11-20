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
    INVALID_LOGIN_METHOD("AUTH_005", "올바르지 않은 로그인 방식입니다.", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE("AUTH_006", "활동 상태인 사용자가 아닙니다.", HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATED("AUTH__007", "이미 탈퇴한 계정입니다.", HttpStatus.BAD_REQUEST),
    NO_CORRESPONDING_PASSWORD_VALUE("AUTH_008", "패스워드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_AUTHENTICATED_USER("AUTH_009", "인증을 받지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_AUTH_CODE("AUTH_010", "유효하지 않은 인증 코드입니다.", HttpStatus.UNAUTHORIZED),
    FAILED_TO_FETCH_USER_INFO("AUTH_011", "OAuth 2.0 서비스로부터 사용자 정보를 불러오는 데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_CORRESPONDING_NAVER_STATE("AUTH_012", "네이버 State 값이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_EXISTS_ACCESS_TOKEN("AUTH_013", "액세스 토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    DIFFERENT_LOGIN_METHOD("AUTH_014", "다른 로그인 방식으로 가입된 계정입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
