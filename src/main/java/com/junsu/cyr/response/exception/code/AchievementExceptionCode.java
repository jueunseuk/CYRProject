package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AchievementExceptionCode implements ExceptionCode {
    NOT_FOUND_ACHIEVEMENT("ACHIEV_001", "해당 업적을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ACHIEVEMENT_REWARD("ACHIEV_002", "해당 업적의 보상을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ACHIEVEMENT_LOG("ACHIEV_002", "해당 업적 로그를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
