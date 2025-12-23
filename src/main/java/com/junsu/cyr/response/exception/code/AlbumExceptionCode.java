package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AlbumExceptionCode implements ExceptionCode {
    TOO_SHORT_TITLE("ALBUM_001", "앨범의 제목이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_IMAGE_URL("ALBUM_002", "앨범의 이미지가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    INCORRECT_RELEASE_DATE("ALBUM_003", "앨범의 발매일이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_ALBUM_ID("ALBUM_004", "해당 앨범 아이디는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_SEARCH_CONDITION("ALBUM_005", "앨범 조회 조건이 잘못 되었습니다.", HttpStatus.BAD_REQUEST),
    TOO_SHORT_INTRODUCTION("ALBUM_006", "앨범 소개가 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    FAILED_TO_UPLOAD_ALBUM("ALBUM_007", "앨범을 업로드 하는 도중 문제가 발생했습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
