package com.junsu.cyr.domain.cheers;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cheer")
public class Cheer extends BaseTime {
    @Id
    @Column(name = "cheer_id")
    private Integer cheerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "sum")
    private Long sum;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void updateCheer() {
        this.updatedAt = LocalDateTime.now();
        this.sum += 1;
    }
}
