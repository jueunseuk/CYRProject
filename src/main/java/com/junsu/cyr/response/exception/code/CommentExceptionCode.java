package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentExceptionCode implements ExceptionCode {
    TOO_SHORT_COMMENT("COM_001", "댓글이 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_COMMENT("COM_002", "해당 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DO_NOT_HAVE_PERMISSION("COM_003", "권한이 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
