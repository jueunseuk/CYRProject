package com.junsu.cyr.domain.events;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.EventExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_comment")
public class EventComment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_comment_id")
    private Long eventCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event", nullable = false)
    private Event event;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "imageUrl")
    private String imageUrl;

    public void updateImageUrl(String imageUrl) {
        if(imageUrl == null) {
            throw new BaseException(EventExceptionCode.INVALID_REQUEST);
        }
        this.imageUrl = imageUrl;
    }
}
