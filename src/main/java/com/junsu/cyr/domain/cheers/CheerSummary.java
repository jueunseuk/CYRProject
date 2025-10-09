package com.junsu.cyr.domain.cheers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cheer_summary")
public class CheerSummary {
    @EmbeddedId
    private CheerSummaryId cheerSummaryId;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void increase() {
        this.count = this.count == null ? 1 : this.count + 1;
        this.updatedAt = LocalDateTime.now();
    }
}
