package com.junsu.cyr.repository;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryImageRepository extends JpaRepository<GalleryImage, Integer> {
    @Query("select gi.url from GalleryImage as gi where gi.gallery.galleryId = :galleryId")
    List<String> findGalleryImageList(Long galleryId);

    @Query("select gi from GalleryImage as gi where gi.gallery.galleryId = :galleryId")
    List<GalleryImage> findGalleryImage(Long galleryId);
}
