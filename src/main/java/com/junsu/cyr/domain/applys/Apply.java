package com.junsu.cyr.domain.applys;

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
@Table(name = "apply")
public class Apply extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long applyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "motive", nullable = false)
    private String motive;

    @Column(name = "primary_time", nullable = false)
    private String primaryTime;

    @Column(name = "weekly_hour", nullable = false)
    private String weeklyHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "preference_role", nullable = false)
    private PreferenceRole preferenceRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "preference_method", nullable = false)
    private PreferenceMethod preferenceMethod;

    @Column(name = "contact", nullable = false)
    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm_user")
    private User confirmUser;

    @Column(name = "confirm")
    private Boolean confirm;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    public void confirm(User user) {
        this.confirmUser = user;
        this.confirm = true;
        this.confirmedAt = LocalDateTime.now();
    }
}
