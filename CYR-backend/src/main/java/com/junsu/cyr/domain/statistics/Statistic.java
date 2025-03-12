package com.junsu.cyr.domain.statistics;

import com.junsu.cyr.domain.globals.BaseTime;
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
@Table(name = "statistic")
public class Statistic extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistic_id", nullable = false)
    private Long statisticId;

    @Column(name = "total_member")
    private Long totalMember;

    @Column(name = "today_view")
    private Long todayView;

    @Column(name = "total_view")
    private Long totalView;

    @Column(name = "today_member")
    private Long todayMember;

    @Column(name = "total_cheer")
    private Long totalCheer;
}
