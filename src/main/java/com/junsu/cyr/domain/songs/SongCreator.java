package com.junsu.cyr.domain.songs;

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
@Table(name = "song_creator")
public class SongCreator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_creator_id")
    private Integer songCreatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song")
    private Song song;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
}
