package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SongExceptionCode implements ExceptionCode {
    TOO_SHORT_TITLE("SONG_001", "노래의 제목이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_LINK("SONG_002", "노래의 관련 링크가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_RELEASED_CHECK("SONG_003", "발매 또는 미발매 여부가 정확하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_SEQUENCE("SONG_004", "곡의 순서가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_SEARCH_CONDITION("SONG_005", "음악 검색 조건이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_SONG_ID("SONG_006", "해당 노래 아이디는 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_ALBUM("SONG_007", "노래의 앨범이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_BE_CHANGED_TO_UNRELEASED("SONG_008", "발매곡은 미발매곡을 바꿀 수 없습니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_LYRICS("SONG_009", "가사가 존재하지 않거나 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
