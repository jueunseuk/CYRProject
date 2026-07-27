package com.junsu.cyr.domain.songs;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.response.exception.code.AlbumExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "album")
public class Album extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "album_type")
    private AlbumType albumType;

    @Column(name = "released_at")
    private LocalDateTime releasedAt;

    @Column(name = "song_cnt")
    private Integer songCnt;

    public static Album of(String title, String imageUrl, LocalDateTime releasedAt, String introduction, AlbumType albumType) {
        validateTitle(title);
        validateIntroduction(introduction);
        validateImageUrl(imageUrl);

        return Album.builder()
                .title(title)
                .imageUrl(imageUrl)
                .releasedAt(releasedAt)
                .introduction(introduction)
                .albumType(albumType)
                .build();
    }

    public void updateSongCnt(Integer songCnt) {
        this.songCnt = songCnt;
    }

    public void updateTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void updateImageUrl(String imageUrl) {
        validateImageUrl(imageUrl);
        this.imageUrl = imageUrl;
    }

    private static void validateTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_TITLE);
        }
    }

    private static void validateIntroduction(String introduction) {
        if(introduction == null || introduction.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_INTRODUCTION);
        }
    }

    private static void validateImageUrl(String imageUrl) {
        if(imageUrl == null || imageUrl.isEmpty()) {
            throw new BaseException(AlbumExceptionCode.TOO_SHORT_IMAGE_URL);
        }
    }
}
