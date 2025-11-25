package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserInventoryExceptionCode implements ExceptionCode {
    NOT_FOUND_USER_INVENTORY_ITEM("USERi_001", "사용자가 해당 아이템을 보유하지 않았습니다.", HttpStatus.NOT_FOUND),
    INSUFFICIENT_NUMBER_OF_ITEMS("USERI_002", "보유한 아이템의 개수가 부족합니다.", HttpStatus.BAD_REQUEST),
    INVALID_USE_REQUEST("USERI_003", "필요한 요청값이 존재하지 않거나 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
