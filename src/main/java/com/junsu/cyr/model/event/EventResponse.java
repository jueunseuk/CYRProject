package com.junsu.cyr.model.event;

import com.junsu.cyr.domain.events.Event;
import com.junsu.cyr.domain.events.Status;
import com.junsu.cyr.domain.events.Type;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {
    private Long eventId;
    private Type type;
    private Status status;
    private String title;
    private String content;
    private Integer maxUser;
    private Boolean useComment;
    private Long commentCnt;
    private Long viewCnt;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private Boolean fixed;
    private Boolean locked;
    private Integer userId;
    private String nickname;

    public EventResponse(Event event) {
        this.eventId = event.getEventId();
        this.type = event.getType();
        this.status = event.getStatus();
        this.title = event.getTitle();
        this.content = event.getContent();
        this.maxUser = event.getMaxUser();
        this.useComment = event.getUseComment();
        this.commentCnt = event.getCommentCnt();
        this.viewCnt = event.getViewCnt();
        this.createdAt = event.getCreatedAt();
        this.closedAt = event.getClosedAt();
        this.fixed = event.getFixed();
        this.locked = event.getLocked();
        this.userId = event.getUser().getUserId();
        this.nickname = event.getUser().getNickname();
    }
}
