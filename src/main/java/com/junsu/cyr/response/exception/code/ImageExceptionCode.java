package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageExceptionCode implements ExceptionCode {
    FAILED_TO_UPLOAD_IMAGE("IMAGE_001", "이미지를 S3에 업로드하는 데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DO_NOT_HAVE_PERMISSION_TO_UPLOAD("IMAGE_002", "이미지를 업로드할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NO_PHOTOS_TO_UPLOAD("IMAGE_003", "업로드할 이미지가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
