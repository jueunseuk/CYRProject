package com.junsu.cyr.repository;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GalleryImageRepository extends JpaRepository<GalleryImage, Integer> {
    @Query("select gi.url from GalleryImage as gi where gi.gallery.galleryId = :galleryId")
    List<String> findGalleryImageList(Long galleryId);

    @Query("select gi from GalleryImage as gi where gi.gallery.galleryId = :galleryId")
    List<GalleryImage> findGalleryImage(Long galleryId);

    @Query("SELECT COUNT(gi) FROM GalleryImage gi JOIN gi.gallery g WHERE g.user = :user")
    Long countByUser(User user);

    Page<GalleryImage> findAllByGallery_User(User user, Pageable pageable);

    @Query("select count(gi) from GalleryImage gi where gi.gallery = :gallery")
    Long countByGalleryImageId(Gallery gallery);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime now);
}
