package com.junsu.cyr.domain.calendar;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calendar_request")
public class CalendarRequest extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_request_id", nullable = false)
    private Long calendarRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateStatus() {
        this.status = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
        this.status = false;
    }
}
