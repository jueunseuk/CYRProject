package com.junsu.cyr.domain.events;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
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
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event")
    private Event event;

    @Column(name = "content")
    private String content;
}
