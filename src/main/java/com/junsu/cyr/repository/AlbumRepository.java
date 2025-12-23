package com.junsu.cyr.repository;

import com.junsu.cyr.domain.songs.Album;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findAllByReleasedAtBetween(LocalDate start, LocalDate end, Pageable pageable);
    Album findByTitle(String title);
    List<Album> findAllByOrderByReleasedAt();
}
