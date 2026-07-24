package com.junsu.cyr.model.qna;

import lombok.Data;

@Data
public class QuestionUploadRequest {
    private String title;
    private String content;
    private Integer sandCnt;
}
