package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostExceptionCode implements ExceptionCode {
    POST_NOT_BE_FOUND("POST_001", "삭제되거나 존재하지 않는 게시글입니다.", HttpStatus.BAD_REQUEST),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
