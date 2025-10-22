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

    @Column(name = "today_member")
    private Long todayMember;

    @Column(name = "total_post")
    private Long totalPost;

    @Column(name = "today_post")
    private Long todayPost;

    @Column(name = "total_comment")
    private Long totalComment;

    @Column(name = "today_comment")
    private Long todayComment;

    @Column(name = "total_gallery")
    private Long totalGallery;

    @Column(name = "today_gallery")
    private Long todayGallery;

    @Column(name = "total_convert")
    private Long totalConvert;

    @Column(name = "today_convert")
    private Long todayConvert;
}
