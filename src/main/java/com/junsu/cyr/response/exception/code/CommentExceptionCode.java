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
    INVALID_REQUEST("COM_004", "유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
    FIX_COMMENT_NUMBER_EXCEEDED("COM_005", "고정 가능한 댓글 개수를 초과했습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
