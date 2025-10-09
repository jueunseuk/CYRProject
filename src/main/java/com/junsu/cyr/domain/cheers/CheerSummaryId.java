package com.junsu.cyr.domain.cheers;

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
@EqualsAndHashCode
public class CheerSummaryId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "date")
    private LocalDate date;
}
