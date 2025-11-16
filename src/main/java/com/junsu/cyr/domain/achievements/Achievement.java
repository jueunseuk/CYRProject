package com.junsu.cyr.domain.achievements;

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
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id", nullable = false)
    private Integer achievementId;

    @Column(name = "name", nullable = false, length = 1000)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "condition_amount")
    private Integer conditionAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;
}
