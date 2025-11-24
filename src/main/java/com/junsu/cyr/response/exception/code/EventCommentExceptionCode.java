package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EventCommentExceptionCode implements ExceptionCode {
    NOT_FOUND_EVENT("EVENTC_001", "해당 이벤트의 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    COMMENT_ARE_NOT_AVAILABLE("EVENTC_002", "해당 이벤트는 댓글을 사용할 수 없습니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_COMMENT("EVENTC_003", "댓글의 길이가 존재하지 않거나 짧습니다.", HttpStatus.BAD_REQUEST),
    DO_NOT_HAVE_PERMISSION("EVENTC_004", "댓글을 조작할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ALREADY_UPLOAD_COMMENT("EVENTC_005", "이미 댓글을 등록했습니다.", HttpStatus.CONFLICT),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
