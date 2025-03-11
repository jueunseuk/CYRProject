package com.junsu.cyr.domain.achievements;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "achievement_log")
public class AchievementLog extends BaseTime {
    @EmbeddedId
    private AchievementLogId achievementLogId;
}
