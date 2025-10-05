package com.junsu.cyr.domain.users;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.model.user.UserProfileUpdateRequest;
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
    @Column(name = "method", nullable = false)
    private Method method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "warn")
    private Integer warn;

    @Column(name = "attendance_cnt")
    private Integer attendanceCnt;

    @Column(name = "consecutive_attendance_cnt", nullable = false)
    private Integer consecutiveAttendanceCnt;

    @Column(name = "cheer_cnt")
    private Long cheerCnt;

    @Column(name = "user_deleted_at")
    private LocalDateTime deletedAt;

    public void updatePassword(String password) {
        this.password = password;
        this.passwordUpdatedAt = LocalDateTime.now();
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void updateInformation(UserProfileUpdateRequest request) {
        this.age = request.getAge() == null ? this.age : request.getAge();
        this.gender = request.getGender() == null ? this.gender : request.getGender();
        this.nickname = request.getNickname() == null ? this.nickname : request.getNickname();
        this.introduction = request.getIntroduction() == null ? this.introduction : request.getIntroduction();
        this.name = request.getName() == null ? this.name : request.getName();
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
    }

    public void initConsecutiveAttendanceCnt() {
        this.consecutiveAttendanceCnt = 1;
    }

    public void increaseExpCnt(Integer amount) {
        this.epxCnt += amount;
    }

    public void updateSand(Integer amount) {
        this.sand += amount;
        if(this.sand < 0) {
            this.sand = 0;
        }
    }

    public void increaseGlass() {
        this.glass++;
    }

    public void decreaseGlass() {
        this.glass--;
    }

    public void updateTemperature(Integer amount) {
        this.temperature += amount;
        if(temperature > 1800) {
            this.temperature = 1800;
        } else if(temperature < 0) {
            this.temperature = 0;
        }
    }

    public void initTemperature() {
        this.temperature = 0;
    }
}
