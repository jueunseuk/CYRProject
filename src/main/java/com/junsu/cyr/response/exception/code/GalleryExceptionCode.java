package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GalleryExceptionCode implements ExceptionCode {
    NO_EXIST_IMAGE("GALLERY_001", "업로드할 이미지가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    ERROR_UPLOADING_GALLERY_IMAGE("GALLERY_002", "갤러리 이미지 업로드 도중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_EXIST_GALLERY("GALLERY_003", "해당 갤러리가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    REQUESTED_PERSON_IS_NOT_AUTHOR("GALLERY_004", "요청한 사람의 아이디가 작성자의 아이디와 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
