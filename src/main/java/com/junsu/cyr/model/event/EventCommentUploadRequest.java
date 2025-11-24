package com.junsu.cyr.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCommentUploadRequest {
    private String content;
    private MultipartFile file;
}
