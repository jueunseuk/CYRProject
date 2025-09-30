package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExperienceLogExceptionCode implements ExceptionCode {
    NOT_FOUND_EXPERIENCE_LOG("EXPL_001", "해당 경험치 변경 로그는 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
