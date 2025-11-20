package com.junsu.cyr.util;

import com.junsu.cyr.response.exception.code.PaginationExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableMaker {
    private static final Integer PAGE = 0;
    private static final Integer SIZE = 100;
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    public static Pageable of(String sort) {
        return of(PAGE, SIZE, sort);
    }

    public static Pageable of(String sort, String direction) {
        validDirection(direction);
        return of(PAGE, SIZE, sort, direction);
    }

    public static Pageable of(Integer page, Integer size) {
        return PageRequest.of(page, size);
    }

    public static Pageable of(Integer page, Integer size, String sort) {
        isNegative(page);
        isNegative(size);
        return of(page, size, sort, ASC);
    }

    public static Pageable of(Integer page, Integer size, String sort, String direction) {
        isNegative(page);
        isNegative(size);
        validDirection(direction);
        Sort s = Sort.by(Sort.Direction.fromString(direction), sort);
        return PageRequest.of(page, size, s);
    }

    private static void isNegative(Integer number) {
        if(number < 0) {
            throw new BaseException(PaginationExceptionCode.CANNOT_USE_NEGATIVE_PARAMETER);
        }
    }

    private static void validDirection(String direction) {
        if(!direction.equals(ASC) && !direction.equals(DESC)) {
            throw new BaseException(PaginationExceptionCode.CANNOT_USE_DIRECTION_VALUE);
        }
    }
}
