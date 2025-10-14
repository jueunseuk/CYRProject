package com.junsu.cyr.service.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.gallery.*;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.GalleryRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final GalleryImageRepository galleryImageRepository;
    private final S3Service s3Service;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public void uploadGallery(GalleryUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        if(request.getImages().isEmpty()) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY);
        }

        Gallery gallery = Gallery.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .viewCnt(0L)
                .picturedAt(LocalDateTime.parse(request.getPicturedAt()))
                .type(request.getType())
                .build();

        galleryRepository.save(gallery);

        user.updateImageCnt(0L, request.getImages().size());

        for(int i = 0; i < request.getImages().size(); i++) {
            userService.addExpAndSand(user, 3, 11);
        }

        uploadFileAndCreateGalleryImage(request, gallery, 0);
    }

    public Page<GalleryImageResponse> getAllGalleryImages(GalleryImageRequest condition) {
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(condition.getSort()).descending());

        Page<GalleryImage> galleryImages = galleryImageRepository.findAll(pageable);

        return galleryImages.map(GalleryImageResponse::new);
    }

    @Transactional
    public GalleryResponse getGallery(Long galleryId) {
        Gallery gallery = galleryRepository.findByGalleryId(galleryId)
                .orElseThrow(() -> new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY));

        List<String> galleryImages = galleryImageRepository.findGalleryImageList(galleryId);

        if(galleryImages.isEmpty()) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_IMAGE);
        }

        gallery.updateViewCnt();

        return new GalleryResponse(gallery, galleryImages);
    }

    @Transactional
    public void deleteGallery(Long galleryId, Integer userId) {
        Gallery gallery = galleryRepository.findByGalleryId(galleryId)
                .orElseThrow(() -> new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY));

        if(!gallery.getUser().getUserId().equals(userId)) {
            throw new BaseException(GalleryExceptionCode.REQUESTED_PERSON_IS_NOT_AUTHOR);
        }

        List<GalleryImage> galleryImages = galleryImageRepository.findGalleryImage(galleryId);

        galleryImageRepository.deleteAll(galleryImages);
        galleryRepository.delete(gallery);
    }

    @Transactional
    public void updateGallery(Long galleryId, GalleryUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Gallery gallery = galleryRepository.findByGalleryId(galleryId)
                .orElseThrow(() -> new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY));

        if (!gallery.getUser().getUserId().equals(userId)) {
            throw new BaseException(GalleryExceptionCode.REQUESTED_PERSON_IS_NOT_AUTHOR);
        }

        gallery.updateGallery(
            request.getTitle(),
            request.getDescription(),
            LocalDateTime.parse(request.getPicturedAt())
        );

        if(request.getImages() != null) {
            Long originGalleryImageCnt = galleryImageRepository.countByGalleryImageId(gallery);
            user.updateImageCnt(originGalleryImageCnt, request.getImages().size());
        }

        List<GalleryImage> oldImages = galleryImageRepository.findGalleryImage(galleryId);
        for(GalleryImage galleryImage : oldImages) {
            galleryImage.updatePicturedAt(LocalDateTime.parse(request.getPicturedAt()));
        }

        List<GalleryImage> toDelete = oldImages.stream()
                .filter(img -> !request.getOriginalImages().contains(img.getUrl()))
                .toList();
        galleryImageRepository.deleteAll(toDelete);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            uploadFileAndCreateGalleryImage(request, gallery, request.getOriginalImages().size());
            for (int i = 0; i < request.getImages().size(); i++) {
                userService.addExpAndSand(user, 3, 11);
            }
        }
    }

    private void uploadFileAndCreateGalleryImage(GalleryUploadRequest request, Gallery gallery, Integer startSequence) {
        List<String> imageUrls;
        try {
            imageUrls = s3Service.uploadFiles(request.getImages(), Type.CYR);
        } catch (Exception e) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_IMAGE);
        }

        if (imageUrls.isEmpty()) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_IMAGE);
        }

        List<GalleryImage> newImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            GalleryImage galleryImage = GalleryImage.builder()
                    .gallery(gallery)
                    .url(imageUrls.get(i))
                    .sequence(startSequence + i + 1)
                    .picturedAt(gallery.getPicturedAt())
                    .build();

            newImages.add(galleryImage);
        }

        galleryImageRepository.saveAll(newImages);
    }

    public Page<GalleryImageResponse> getImagesByUser(Integer searchId, GallerySearchConditionRequest condition) {
        User user = userRepository.findByUserId(searchId)
                .orElseThrow(() -> new BaseException(UserExceptionCode.NOT_EXIST_USER));

        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort()));

        Page<GalleryImage> galleryImages = galleryImageRepository.findAllByGallery_User(user, pageable);

        return galleryImages.map(GalleryImageResponse::new);
    }

    public List<GalleryImageResponse> getRandomImages(Integer amount) {
        List<GalleryImage> galleryImages = galleryImageRepository.findAll();
        List<GalleryImageResponse> galleryImageResponses = new ArrayList<>();

        int size = galleryImages.size();
        Set<Integer> set = new HashSet<>();
        while(set.size() < amount) {
            int random = (int) (Math.random() * size);

            if(!set.contains(random)) {
                set.add(random);
                galleryImageResponses.add(new GalleryImageResponse(galleryImages.get(random)));
            }
        }

        return galleryImageResponses;
    }
}