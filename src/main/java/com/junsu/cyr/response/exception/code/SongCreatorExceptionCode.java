package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SongCreatorExceptionCode implements ExceptionCode {
    TOO_SHORT_NAME("SONGC_001", "창작자의 이름이 너무 짧거나 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_MAPPING_TO_SONG("SONGC_002", "창작자를 노래와 연관시킬 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_SONG_CREATOR("SONGC_003", "해당 창작자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_SONG_CREATOR_TYPE("SONGC_004", "창작자의 타입이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
