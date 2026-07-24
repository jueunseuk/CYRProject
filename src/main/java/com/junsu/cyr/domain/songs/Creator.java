package com.junsu.cyr.domain.songs;

import com.junsu.cyr.response.exception.code.CreatorExceptionCode;
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
@Table(name = "creator")
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id")
    private Integer creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song")
    private Song song;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "creator_role")
    private CreatorRole creatorRole;

    public static Creator of(Song song, String name, CreatorRole creatorRole) {
        validateSong(song);
        validateName(name);
        validateRole(creatorRole);
        return Creator.builder()
                .song(song)
                .name(name)
                .creatorRole(creatorRole)
                .build();
    }

    private static void validateSong(Song song) {
        if(song == null) {
            throw new BaseException(CreatorExceptionCode.INVALID_SONG);
        }
    }

    private static void validateName(String name) {
        if(name == null || name.isEmpty()) {
            throw new BaseException(CreatorExceptionCode.TOO_SHORT_NAME);
        }
    }

    private static void validateRole(CreatorRole creatorRole) {
        if(creatorRole == null) {
            throw new BaseException(CreatorExceptionCode.INVALID_SONG_CREATOR_TYPE);
        }
    }
}
