package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AnnouncementExceptionCode implements ExceptionCode {
    NOT_FOUND_ANNOUNCEMENT("ANN_001", "해당 공지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ANNOUNCEMENT_CATEGORY("ANN_002", "해당 공지 카테고리를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TOO_SHORT("ANN_003", "제목 또는 본문의 길이가 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_BE_NULL("ANN_004", "NULL 값이 될 수 없습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
