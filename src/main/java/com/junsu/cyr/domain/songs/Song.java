package com.junsu.cyr.domain.songs;

import com.junsu.cyr.response.exception.code.SongExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Integer songId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album")
    private Album album;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "is_title")
    private Boolean isTitle;

    @Column(name = "lyrics", columnDefinition = "TEXT")
    private String lyrics;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    public static Song of(Album album, String imageUrl, String title, String link, Integer sequence, Boolean isTitle, String lyrics, String introduction) {
        validateTitle(title);
        validateLink(link);
        validateSequence(sequence);
        validateLyrics(lyrics);
        return Song.builder()
                .album(album)
                .imageUrl(imageUrl)
                .title(title)
                .link(link)
                .sequence(sequence)
                .isTitle(isTitle)
                .lyrics(lyrics)
                .introduction(introduction)
                .build();
    }

    public void updateTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void updateIsTitle(Boolean isTitle) {
        this.isTitle = isTitle;
    }

    public void updateLink(String link) {
        validateLink(link);
        this.link = link;
    }

    public void updateSequence(Integer sequence) {
        validateSequence(sequence);
        this.sequence = sequence;
    }

    public void updateLyrics(String lyrics) {
        validateLyrics(lyrics);
        this.lyrics = lyrics;
    }

    public void updateAlbum(Album album) {
        this.album = album;
    }

    private static void validateTitle(String title) {
        if(title == null || title.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_TITLE);
        }
    }

    private static void validateLink(String link) {
        if(link == null || link.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_LINK);
        }
    }

    private static void validateSequence(Integer sequence) {
        if(sequence == null || sequence < 0) {
            throw new BaseException(SongExceptionCode.INVALID_SEQUENCE);
        }
    }

    private static void validateLyrics(String lyrics) {
        if(lyrics == null || lyrics.isEmpty()) {
            throw new BaseException(SongExceptionCode.TOO_SHORT_LYRICS);
        }
    }
}
