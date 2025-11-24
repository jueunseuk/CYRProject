package com.junsu.cyr.model.event;

import com.junsu.cyr.domain.events.Status;
import com.junsu.cyr.domain.events.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventUploadRequest {
    private String title;
    private String content;
    private String closedAt;
    private Status status = Status.ACTIVE;
    private Type type = Type.GENERAL;
    private Integer maxUser;
    private Boolean useComment;
    private Boolean fixed;
    private Boolean locked;
}
