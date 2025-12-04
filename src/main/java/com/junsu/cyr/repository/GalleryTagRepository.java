package com.junsu.cyr.repository;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryTagRepository extends JpaRepository<GalleryTag, Integer> {
    List<GalleryTag> findAllByGallery(Gallery gallery);
    List<GalleryTag> findAllByTag_Name(String name, Pageable pageable);
}
