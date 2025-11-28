package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PollExceptionCode implements ExceptionCode {
    NOT_FOUND_POLL("POLL_001", "해당 투표를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_ALLOWED_TO_MAKE_POLL("POLL_002", "투표를 만들 권한이 업습니다.", HttpStatus.FORBIDDEN),
    INSUFFICIENT_TO_OPTIONS("POLL_003", "옵션의 수가 불충분합니다.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_TO_DESCRIBE("POLL_004", "투표에 대한 설명이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_CLOSED_AT("POLL_005", "투표 종료 시각을 과거로 설정할 수 없습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_PARTICIPATING_VOTE("POLL_006", "이미 참여한 투표입니다.", HttpStatus.CONFLICT),
    UNABLE_TO_AGGREGATE_POLL_STATE("POLL_007", "집계할 수 없는 투표 상태입니다.", HttpStatus.BAD_REQUEST),
    NO_VOTES_TO_AGGREGATE("POLL_008", "집계할 투표 로그가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    POLL_AND_OPTION_MISMATCH("POLL_009", "투표와 투표 옵션이 불일치합니다.", HttpStatus.BAD_REQUEST),
    INVALID_VALUE_INJECTION("POLL_010", "올바르지 않은 값 주입 시도입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
