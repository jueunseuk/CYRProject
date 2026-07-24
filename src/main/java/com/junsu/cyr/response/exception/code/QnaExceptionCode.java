package com.junsu.cyr.response.exception.code;

import com.junsu.cyr.response.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QnaExceptionCode implements ExceptionCode {
    INVALID_TITLE("QUEST_001", "질문의 제목이 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    INVALID_CONTENT("QUEST_002", "질문 내용이 너무 짧습니다.", HttpStatus.BAD_REQUEST),
    INVALID_CLOSE_TIME("QUEST_003", "마감 시간이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_SAND_CNT("QUEST_004", "질문에 걸린 모래알 개수가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    DO_NOT_HAVE_PERMISSION("QUEST_005", "요청을 실행할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND_RESOURCE("QUEST_006", "해당 질문을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MISMATCH_QUESTION_AND_ANSWER("QUEST_007", "답변 대상이 질문과 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    CANNOT_ADOPT_MYSELF("QUEST_008", "자신이 쓴 질문에 자신이 쓴 답변은 채택할 수 없습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_ADOPT_ANSWER_EXIST("QUEST_009", "이미 채택된 답변이 존재합니다.", HttpStatus.BAD_REQUEST),
    INVALID_STATUS("QUEST_010", "상태를 변경할 수 없습니다.", HttpStatus.BAD_REQUEST),
    CLOSED_QUESTION("QUEST_011", "답변 받기가 끝난 질문입니다.", HttpStatus.BAD_REQUEST),
    NOT_ENOUGH_SAND("QUEST_012", "모래알을 충분히 보유하고 있지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
