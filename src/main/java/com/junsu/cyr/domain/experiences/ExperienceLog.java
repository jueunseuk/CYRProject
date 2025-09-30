package com.junsu.cyr.domain.experiences;

import com.junsu.cyr.domain.globals.BaseTime;
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
public class ExperienceLog extends BaseTime {
    @EmbeddedId
    private ExperienceLogId experienceLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience")
    private Experience experience;

    @Column(name = "after", nullable = false)
    private Long after;
}
