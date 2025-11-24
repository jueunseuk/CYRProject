package com.junsu.cyr.domain.events;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "max_user")
    private Integer maxUser;

    @Column(name = "use_comment")
    private Boolean useComment;

    @Column(name = "view_cnt")
    private Long viewCnt;

    @Column(name = "comment_cnt")
    private Long commentCnt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "fixed")
    private Boolean fixed;

    @Column(name = "locked")
    private Boolean locked;

    public void increaseViewCnt() {
        this.viewCnt++;
    }

    public void increaseCommentCnt() {
        this.commentCnt++;
    }

    public void decreaseCommentCnt() {
        this.commentCnt--;
        if(this.commentCnt < 0) {
            this.commentCnt = 0L;
        }
    }
}
