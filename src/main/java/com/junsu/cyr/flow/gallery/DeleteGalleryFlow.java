package com.junsu.cyr.flow.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.GalleryRepository;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.gallery.GalleryService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteGalleryFlow {

    private final GalleryRepository galleryRepository;
    private final GalleryImageRepository galleryImageRepository;
    private final UserService userService;
    private final GalleryService galleryService;

    @Transactional
    public void deleteGallery(Long galleryId, Integer userId) {
        User user = userService.getUserById(userId);

        Gallery gallery = galleryService.getGalleryByGalleryId(galleryId);

        if(gallery.getUser() != user) {
            throw new BaseException(GalleryExceptionCode.REQUESTED_PERSON_IS_NOT_AUTHOR);
        }

        List<GalleryImage> galleryImages = galleryImageRepository.findGalleryImage(galleryId);

        galleryImageRepository.deleteAll(galleryImages);
        galleryRepository.delete(gallery);
    }
}
