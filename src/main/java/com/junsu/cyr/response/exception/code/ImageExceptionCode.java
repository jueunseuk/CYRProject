package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageExceptionCode implements ExceptionCode {
    FAILED_TO_UPLOAD_IMAGE("IMAGE_001", "이미지를 S3에 업로드하는 데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR)

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
