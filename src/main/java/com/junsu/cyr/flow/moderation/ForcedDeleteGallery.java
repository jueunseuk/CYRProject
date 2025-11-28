package com.junsu.cyr.flow.moderation;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.GalleryRepository;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.gallery.GalleryService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForcedDeleteGallery {

    private final UserService userService;
    private final GalleryService galleryService;
    private final GalleryImageRepository galleryImageRepository;
    private final GalleryRepository galleryRepository;

    @Transactional
    public void forcedDeleteGallery(Long galleryId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        Gallery gallery = galleryService.getGalleryByGalleryId(galleryId);
        List<GalleryImage> galleryImages = galleryImageRepository.findGalleryImage(galleryId);

        galleryImageRepository.deleteAll(galleryImages);
        galleryRepository.delete(gallery);
    }
}
