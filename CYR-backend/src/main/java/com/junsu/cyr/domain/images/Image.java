package com.junsu.cyr.domain.images;

import com.junsu.cyr.domain.posts.Post;
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
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_index", nullable = false)
    private Integer imageIndex;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_created_at", nullable = false, updatable = false)
    private LocalDateTime imageCreatedAt;
}
