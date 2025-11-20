package com.junsu.cyr.domain.rankings;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
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
@Table(name = "ranking")
public class Ranking extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id")
    private Integer rankingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private RankingCategory rankingCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "score")
    private Long score;

    @Column(name = "priority")
    private Long priority;
}
