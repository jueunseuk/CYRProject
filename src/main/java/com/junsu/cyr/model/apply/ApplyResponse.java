package com.junsu.cyr.model.apply;

import com.junsu.cyr.domain.applys.Apply;
import com.junsu.cyr.domain.applys.PreferenceMethod;
import com.junsu.cyr.domain.applys.PreferenceRole;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplyResponse {
    private Long applyId;
    private String title;
    private String motive;
    private String primaryTime;
    private String weeklyHour;
    private PreferenceRole preferenceRole;
    private PreferenceMethod preferenceMethod;
    private String contact;
    private LocalDateTime createdAt;
    private Boolean confirm;
    private LocalDateTime confirmedAt;
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private Integer warnCnt;
    private Integer confirmUserId;
    private String confirmUserNickname;

    public ApplyResponse(Apply apply) {
        this.applyId = apply.getApplyId();
        this.title = apply.getTitle();
        this.motive = apply.getMotive();
        this.primaryTime = apply.getPrimaryTime();
        this.weeklyHour = apply.getWeeklyHour();
        this.preferenceRole = apply.getPreferenceRole();
        this.preferenceMethod = apply.getPreferenceMethod();
        this.contact = apply.getContact();
        this.createdAt = apply.getCreatedAt();
        this.confirm = apply.getConfirm();
        this.confirmedAt = apply.getConfirmedAt();

        User user = apply.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.warnCnt = user.getWarn();

        if(apply.getConfirmUser() != null) {
            User confirmUser = apply.getConfirmUser();
            this.confirmUserId = confirmUser.getUserId();
            this.confirmUserNickname = confirmUser.getNickname();
        }
    }

    public ApplyResponse(Long applyId, String title, PreferenceRole preferenceRole, Boolean confirm, LocalDateTime confirmedAt, Integer userId, String nickname, LocalDateTime createdAt) {
        this.applyId = applyId;
        this.title = title;
        this.preferenceRole = preferenceRole;
        this.confirm = confirm;
        this.confirmedAt = confirmedAt;
        this.userId = userId;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
