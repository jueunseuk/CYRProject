package com.junsu.cyr.domain.gallery;

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
@Table(name = "gallery_image")
public class GalleryImage extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_image_id", nullable = false)
    private Long galleryImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery", nullable = false)
    private Gallery gallery;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "pictured_at", nullable = false)
    private LocalDateTime picturedAt;
}
