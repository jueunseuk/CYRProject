package com.junsu.cyr.repository;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findAllByTitle(String title);
    List<Song> findAllByAlbumOrderBySequence(Album album);

    @Query("select s from Song s order by s.album.releasedAt desc, s.sequence asc")
    List<Song> findAllByHierarchyOrder();
}
