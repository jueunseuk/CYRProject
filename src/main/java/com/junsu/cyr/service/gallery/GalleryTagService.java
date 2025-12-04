package com.junsu.cyr.service.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryTag;
import com.junsu.cyr.domain.gallery.Tag;
import com.junsu.cyr.repository.GalleryTagRepository;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryTagService {

    private final GalleryTagRepository galleryTagRepository;

    @Transactional
    public void createGalleryTag(Gallery gallery, Tag tag) {
        if(gallery == null) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY);
        }
        if(tag == null) {
            throw new BaseException(GalleryExceptionCode.NOT_FOUND_TAG);
        }

        GalleryTag galleryTag = GalleryTag.builder()
                .gallery(gallery)
                .tag(tag)
                .build();

        galleryTagRepository.save(galleryTag);
    }

    public List<GalleryTag> getGalleryTagByGallery(Gallery gallery) {
        return galleryTagRepository.findAllByGallery(gallery);
    }

    public List<GalleryTag> getGalleryTagByName(String name, Pageable pageable) {
        return galleryTagRepository.findAllByTag_Name(name, pageable);
    }
}
