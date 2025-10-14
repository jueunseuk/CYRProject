package com.junsu.cyr.domain.gallery;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.images.Type;
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
@Table(name = "gallery")
public class Gallery extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_id", nullable = false)
    private Long galleryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "pictured_at", nullable = false)
    private LocalDateTime picturedAt;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "view_cnt", nullable = false)
    private Long viewCnt;

    public void updateGallery(String title, String description, LocalDateTime picturedAt) {
        this.title = title;
        this.description = description;
        this.picturedAt = picturedAt;
    }

    public void updateViewCnt() {
        this.viewCnt += 1;
    }
}
