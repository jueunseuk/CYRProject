package com.junsu.cyr.domain.experiences;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExperienceLogId implements Serializable {
    @Column(name = "experience_log_created_at", nullable = false, updatable = false)
    private LocalDateTime experienceLogCreatedAt;

    @Column(name = "experience_log_user_id", nullable = false)
    private Integer userId;
}
