package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaginationExceptionCode implements ExceptionCode {
    CANNOT_USE_NEGATIVE_PARAMETER("PAGE_001", "음수 인자를 전달할 수 없습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_USE_DIRECTION_VALUE("PAGE_002", "해당 방향값으로 설정할 수 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
