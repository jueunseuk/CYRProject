package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ShopLogExceptionCode implements ExceptionCode {
    CANNOT_GET_CONSUMABLE_CATEGORY_ITEM("SHOPL_001", "소모품을 조회하는 것은 지원하지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
