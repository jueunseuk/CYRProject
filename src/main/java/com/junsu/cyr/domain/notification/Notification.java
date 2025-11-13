package com.junsu.cyr.domain.notification;

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
@Table(name = "notification")
public class Notification extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "message")
    private String message;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void readNotification() {
        this.isRead = true;
        this.updatedAt = LocalDateTime.now();
    }
}
