package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RankingCategoryExceptionCode implements ExceptionCode {
    NOT_FOUND_RANKING_CATEGORY("RANKC_001", "랭킹 카테고리를 찾지 못했습니다.", HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
