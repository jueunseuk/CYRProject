package com.junsu.cyr.domain.users;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.model.user.UserProfileUpdateRequest;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
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
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "introduction", length = 2000)
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "exp_cnt", nullable = false)
    private Long epxCnt;

    @Column(name = "sand", nullable = false)
    private Integer sand;

    @Column(name = "glass", nullable = false)
    private Integer glass;

    @Column(name = "temperature", nullable = false)
    private Integer temperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private Method method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "post_cnt", nullable = false)
    private Long postCnt;

    @Column(name = "comment_cnt", nullable = false)
    private Long commentCnt;

    @Column(name = "image_cnt", nullable = false)
    private Long imageCnt;

    @Column(name = "empathy_cnt", nullable = false)
    private Long empathyCnt;

    @Column(name = "warn")
    private Integer warn;

    @Column(name = "attendance_cnt")
    private Integer attendanceCnt;

    @Column(name = "consecutive_attendance_cnt", nullable = false)
    private Integer consecutiveAttendanceCnt;

    @Column(name = "max_consecutive_attendance_cnt", nullable = false)
    private Integer maxConsecutiveAttendanceCnt;

    @Column(name = "cheer_cnt")
    private Long cheerCnt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void updatePassword(String password) {
        this.password = password;
        this.passwordUpdatedAt = LocalDateTime.now();
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
    public void updateInformation(Integer age, String nickname, Gender gender, String introduction, String name) {
        if(nickname != null && nickname.length() > 20) {
            throw new BaseException(UserExceptionCode.INVALID_NICKNAME_VALUE);
        }
        if(introduction != null && introduction.length() < 5) {
            throw new BaseException(UserExceptionCode.TOO_SHORT_INTRODUCTION);
        }
        if(age != null && age < 0) {
            throw new BaseException(UserExceptionCode.INVALID_AGE_VALUE);
        }
        if(name != null && name.length() > 10) {
            throw new BaseException(UserExceptionCode.INVALID_NAME_VALUE);
        }

        this.age = age == null ? this.age : age;
        this.gender = gender == null ? this.gender : gender;
        this.nickname = nickname == null ? this.nickname : nickname;
        this.introduction = introduction == null ? this.introduction : introduction;
        this.name = name == null ? this.name : name;
    }

    public void updateActivity(Long postCnt, Long commentCnt, Long empathyCnt, Long imageCnt) {
        if(getPostCnt() < 0 || getCommentCnt() < 0 || getImageCnt() < 0 || getEmpathyCnt() < 0) {
            throw new BaseException(UserExceptionCode.INVALID_VALUE_INJECTION);
        }
        this.postCnt = postCnt;
        this.commentCnt = commentCnt;
        this.imageCnt = empathyCnt;
        this.empathyCnt = imageCnt;
    }

    public void updateToSecession() {
        this.status = Status.SECESSION;
        this.deletedAt = LocalDateTime.now();
    }

    public void updateAttendanceCnt() {
        this.attendanceCnt++;
    }

    public void increaseConsecutiveAttendanceCnt() {
        this.consecutiveAttendanceCnt++;
        if(this.maxConsecutiveAttendanceCnt < this.consecutiveAttendanceCnt) {
            this.maxConsecutiveAttendanceCnt = this.consecutiveAttendanceCnt;
        }
    }

    public void initConsecutiveAttendanceCnt() {
        this.consecutiveAttendanceCnt = 1;
    }

    public void increaseExpCnt(Integer amount) {
        this.epxCnt += amount;
        if(this.epxCnt < 0) {
            throw new BaseException(UserExceptionCode.INVALID_VALUE_INJECTION);
        }
    }

    public void updateSand(Integer amount) {
        if(this.sand == 0 && amount < 0) {
            throw new BaseException(UserExceptionCode.INVALID_VALUE_INJECTION);
        }
        this.sand += amount;
    }

    public void updateTemperature(Integer amount) {
        if(amount % 50 != 0) {
            throw new BaseException(UserExceptionCode.CAN_ONLY_BE_CHANGED_TO_50_UNITS);
        }
        this.temperature += amount;
        if(temperature > 1800) {
            this.temperature = 1800;
        } else if(temperature < 0) {
            this.temperature = 0;
        }
    }

    public void increaseCheerCnt() {
        this.cheerCnt += 1;
    }

    public void updateImageCnt(Long originGalleryImageCnt, int size) {
        this.imageCnt -= originGalleryImageCnt;
        this.imageCnt += size;
    }

    public void convertGlass(Integer amount) {
        this.glass += amount;
        this.temperature = 0;
        this.sand -= 100;
    }

    public void useGlass(Integer amount) {
        this.glass -= amount;
    }

    public void updateWarnCnt(int amount) {
        if(this.warn == 0 && amount < 0) {
            throw new BaseException(UserExceptionCode.WARNING_ALREADY_ZERO);
        }
        this.warn += amount;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void increaseEmpathyCnt() {
        this.empathyCnt += 1;
    }

    public void decreaseEmpathyCnt() {
        this.empathyCnt -= 1;
    }

    public void increasePostCnt() {
        this.postCnt += 1;
    }

    public void decreasePostCnt() {
        this.postCnt -= 1;
        if(this.postCnt < 0) {
            this.postCnt = 0L;
        }
    }

    public void increaseCommentCnt() {
        this.commentCnt += 1;
    }

    public void decreaseCommentCnt() {
        this.commentCnt -= 1;
        if(this.commentCnt < 0) {
            this.commentCnt = 0L;
        }
    }

    public void updateGlass(Integer amount) {
        if(this.glass == 0 && amount < 0) {
            throw new BaseException(UserExceptionCode.INVALID_VALUE_INJECTION);
        }
        this.glass += amount;
    }

    public void delete() {
        this.name = "탈퇴한 사용자";
        this.nickname = "탈퇴한 사용자";
        this.email = "";
        this.password = "";
        this.profileUrl = "";
        this.passwordUpdatedAt = null;
        this.introduction = "";
        this.method = null;
        this.gender = null;
        this.age = 0;
        this.status = Status.DELETED;
        this.glass = 0;
        this.epxCnt = 0L;
        this.sand = 0;
        this.temperature = 0;
        this.cheerCnt = 0L;
        this.empathyCnt = 0L;
        this.warn = 0;
        this.role = null;
        this.imageCnt = 0L;
        this.commentCnt = 0L;
        this.attendanceCnt = 0;
        this.postCnt = 0L;
        this.consecutiveAttendanceCnt = 0;
        this.maxConsecutiveAttendanceCnt = 0;
    }
}
