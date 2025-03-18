package com.junsu.cyr.domain.achievements;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AchievementLogId implements Serializable {
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "achievement_id")
    private Integer achievement_id;
}
