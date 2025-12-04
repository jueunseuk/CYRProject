package com.junsu.cyr.domain.gallery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "gallery_tag", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gallery", "tag"})
})
public class GalleryTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gallery_tag_id")
    private Long gallery_tag_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery")
    private Gallery gallery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag")
    private Tag tag;
}
