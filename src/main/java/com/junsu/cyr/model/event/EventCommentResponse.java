package com.junsu.cyr.model.event;

import com.junsu.cyr.domain.events.EventComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventCommentResponse {
    private Long eventCommentId;
    private String content;
    private String imageUrl;
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private LocalDateTime createdAt;

    public EventCommentResponse(EventComment eventComment) {
        this.eventCommentId = eventComment.getEventCommentId();
        this.content = eventComment.getContent();
        this.imageUrl = eventComment.getImageUrl();
        this.userId = eventComment.getUser().getUserId();
        this.createdAt = eventComment.getCreatedAt();
        this.nickname = eventComment.getUser().getNickname();
        this.profileUrl = eventComment.getUser().getProfileUrl();
    }
}
