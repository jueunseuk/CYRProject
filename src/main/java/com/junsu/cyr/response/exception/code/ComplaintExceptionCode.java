package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ComplaintExceptionCode implements ExceptionCode {
    NOT_FOUND_COMPLAINT("COMP_001", "해당 컴플레인을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    ALREADY_PROCESSED_COMPLAINT("COMP_002", "이미 처리된 컴플레인입니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_MESSAGE_LENGTH("COMP_003", "메세지의 내용이 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
