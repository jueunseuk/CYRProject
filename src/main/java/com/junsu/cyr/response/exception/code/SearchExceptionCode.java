package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SearchExceptionCode implements ExceptionCode {
    NOT_FOUND_KEYWORD("SEARCH_001", "검색할 키워드가 존재하지 않거나, 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    INVALID_SEARCH_TYPE("SEARCH_002", "검색하려는 타입이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
