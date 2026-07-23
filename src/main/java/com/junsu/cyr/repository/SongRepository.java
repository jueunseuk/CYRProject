package com.junsu.cyr.repository;

import com.junsu.cyr.domain.songs.Album;
import com.junsu.cyr.domain.songs.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findAllByTitle(String title);
    List<Song> findAllByAlbum(Album album, Pageable pageable);
    Page<Song> findAll(Pageable pageable);
}
