package com.junsu.cyr.repository;

import com.junsu.cyr.domain.songs.Song;
import com.junsu.cyr.domain.songs.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Integer> {
    List<Creator> findAllBySong(Song song);
    List<Creator> findAllByName(String name);
    List<Creator> findAllBySong_SongIdIn(List<Integer> songIds);
}
