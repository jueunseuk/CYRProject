package com.junsu.cyr.repository;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.SongCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongCreatorRepository extends JpaRepository<SongCreator, Integer> {
    List<SongCreator> findAllBySong(Song song);
    List<SongCreator> findAllByName(String name);
    List<SongCreator> findAllBySong_SongIdIn(List<Integer> songIds);
}
