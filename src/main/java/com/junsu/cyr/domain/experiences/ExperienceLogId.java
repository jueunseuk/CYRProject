package com.junsu.cyr.domain.experiences;

import com.junsu.cyr.domain.globals.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ExperienceLogId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "attended_at", nullable = false)
    private LocalDate attended_at;
}
