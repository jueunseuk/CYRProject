package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RankingExceptionCode implements ExceptionCode {
    NOT_FOUND_RANKING("RANK_001", "랭킹을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    INVALID_PERIOD("RANK_002", "유효하지 않은 기간 요청입니다.", HttpStatus.BAD_REQUEST),
    INVALID_TYPE("RANK_003", "유효하지 않은 랭킹 타입입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
