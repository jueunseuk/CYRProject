package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlassExceptionCode implements ExceptionCode {
    NOT_FOUND_GLASS("GLASSL_001", "해당 유리 획득 경로는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_ENOUGH_SAND("GLASS_002", "모래알의 수량이 부족합니다.", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_TEMPERATURE("GLASS_003", "활동 온도가 불충분합니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
