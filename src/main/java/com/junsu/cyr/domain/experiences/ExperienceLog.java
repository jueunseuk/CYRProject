package com.junsu.cyr.domain.experiences;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "experience_log")
public class ExperienceLog {
    @EmbeddedId
    private ExperienceLogId experienceLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id")
    private Experience experienceId;
}
