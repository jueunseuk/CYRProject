package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ShopItemExceptionCode implements ExceptionCode {
    NOT_FOUND_SHOP_ITEM("SHOPI_001", "해당 아이템을 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    GLASSES_ARE_INSUFFICIENT("SHOPI_002", "유리 조각의 개수가 부족합니다.", HttpStatus.BAD_REQUEST),
    ALREADY_PURCHASED_ITEM("SHOPI_003", "이미 구매한 상품입니다.", HttpStatus.CONFLICT),
    CANNOT_USABLE_ITEM("SHOPI_004", "사용할 수 없는 아이템입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
