package com.junsu.cyr.domain.announcements;

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
@Table(name = "announcement_category")
public class AnnouncementCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_category_id")
    private Integer announcementCategoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "english")
    private String english;

    @Column(name = "description")
    private String description;
}
