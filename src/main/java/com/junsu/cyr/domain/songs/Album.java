package com.junsu.cyr.domain.songs;

import com.junsu.cyr.response.exception.code.AlbumExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "released_at")
    private LocalDate releasedAt;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "agency")
    private String agency;

    public void updateTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_TITLE);
        }
        this.title = title;
    }

    public void updateImageUrl(String imageUrl) {
        if(imageUrl == null || imageUrl.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_IMAGE_URL);
        }
        this.imageUrl = imageUrl;
    }

    public void updateReleasedAt(LocalDate releasedAt) {
        if(releasedAt == null) {
            throw new BaseException(AlbumExceptionCode.INCORRECT_RELEASE_DATE);
        }
        if(releasedAt.isAfter(LocalDate.now().plusDays(7))) {
            throw new BaseException(AlbumExceptionCode.INCORRECT_RELEASE_DATE);
        }
        this.releasedAt = releasedAt;
    }
}
