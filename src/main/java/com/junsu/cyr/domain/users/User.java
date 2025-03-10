package com.junsu.cyr.domain.users;

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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "user_nickname", nullable = false, length = 100)
    private String userNickname;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "user_profile_url")
    private String userProfileUrl;

    @Column(name = "user_introduction", length = 2000)
    private String userIntroduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_gender")
    private Gender userGender;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_exp_cnt", nullable = false)
    private Long userExpCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_method", nullable = false)
    private Method userMethod;

    @Column(name = "user_registered_at", nullable = false, updatable = false)
    private LocalDateTime userRegisteredAt;

    @Column(name = "user_status", nullable = false)
    private Status userStatus;

    @Column(name = "user_role", nullable = false)
    private Role userRole;

    @Column(name = "user_refresh_token")
    private String userRefreshToken;

    @Column(name = "user_warn")
    private Integer userWarn;

    @Column(name = "user_attendance_cnt")
    private Integer userAttendanceCnt;

    @Column(name = "user_deleted_at")
    private LocalDateTime userDeletedAt;
}
