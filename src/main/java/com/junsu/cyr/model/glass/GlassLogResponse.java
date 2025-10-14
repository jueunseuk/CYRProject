package com.junsu.cyr.model.glass;

import com.junsu.cyr.domain.glass.Glass;
import com.junsu.cyr.domain.glass.GlassLog;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GlassLogResponse {
    private Long glassLogId;
    private LocalDateTime createdAt;
    private Integer userId;
    private String nickname;
    private Integer amount;
    private String name;
    private String description;

    public GlassLogResponse(GlassLog glassLog) {
        this.glassLogId = glassLog.getGlassLogId();
        this.createdAt = glassLog.getCreatedAt();
        User user = glassLog.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        Glass glass = glassLog.getGlass();
        this.amount = glass.getAmount();
        this.name = glass.getName();
        this.description = glass.getDescription();
    }
}
