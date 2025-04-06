package com.junsu.cyr.service.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.gallery.GalleryImageRequest;
import com.junsu.cyr.model.gallery.GalleryImageResponse;
import com.junsu.cyr.model.gallery.GalleryResponse;
import com.junsu.cyr.model.gallery.GalleryUploadRequest;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.GalleryRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.image.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final GalleryImageRepository galleryImageRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void uploadGallery(GalleryUploadRequest request, Integer userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        Gallery gallery = Gallery.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .picturedAt(LocalDateTime.parse(request.getPicturedAt()))
                .type(request.getType())
                .build();

        galleryRepository.save(gallery);

        List<String> imageUrls;
        try {
            imageUrls = s3Service.uploadFiles(request.getImages(), Type.CYR);
        } catch(Exception e) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_IMAGE);
        }

        if(imageUrls.isEmpty()) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_IMAGE);
        }

        List<GalleryImage> galleryImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            GalleryImage galleryImage = GalleryImage.builder()
                    .gallery(gallery)
                    .url(imageUrls.get(i))
                    .sequence(i + 1)
                    .picturedAt(gallery.getPicturedAt())
                    .build();

            galleryImages.add(galleryImage);
        }

        galleryImageRepository.saveAll(galleryImages);
    }

    public Page<GalleryImageResponse> getAllGalleryImages(GalleryImageRequest condition) {
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(condition.getSort()).descending());

        Page<GalleryImage> galleryImages = galleryImageRepository.findAll(pageable);

        return galleryImages.map(GalleryImageResponse::new);
    }

    public GalleryResponse getGallery(Long galleryId) {
        Gallery gallery = galleryRepository.findByGalleryId(galleryId)
                .orElseThrow(() -> new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY));

        List<String> galleryImages = galleryImageRepository.findGalleryImageList(galleryId);

        if(galleryImages.isEmpty()) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_IMAGE);
        }

        return new GalleryResponse(gallery, galleryImages);
    }
}
