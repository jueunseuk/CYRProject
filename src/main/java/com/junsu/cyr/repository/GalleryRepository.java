package com.junsu.cyr.repository;

import com.junsu.cyr.domain.gallery.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    Optional<Gallery> findByGalleryId(Long id);

    Page<Gallery> findAllByTitleContaining(String keyword, Pageable pageable);
}
