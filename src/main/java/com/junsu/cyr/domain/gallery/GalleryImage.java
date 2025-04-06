package com.junsu.cyr.domain.gallery;

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
@Table(name = "gallery_image")
public class GalleryImage {
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
}
