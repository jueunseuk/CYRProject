package com.junsu.cyr.flow.gallery;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.gallery.GalleryTag;
import com.junsu.cyr.domain.gallery.Tag;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.gallery.GalleryUploadRequest;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.GalleryTagRepository;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.experience.ExperienceService;
import com.junsu.cyr.service.gallery.GalleryService;
import com.junsu.cyr.service.gallery.GalleryTagService;
import com.junsu.cyr.service.gallery.TagService;
import com.junsu.cyr.service.image.S3Service;
import com.junsu.cyr.service.sand.SandRewardService;
import com.junsu.cyr.service.sand.SandService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateGalleryFlow {

    private final UserService userService;
    private final GalleryService galleryService;
    private final GalleryImageRepository galleryImageRepository;
    private final S3Service s3Service;
    private final SandService sandService;
    private final SandRewardService sandRewardService;
    private final ExperienceService experienceService;
    private final ExperienceRewardService experienceRewardService;
    private final GalleryTagService galleryTagService;
    private final GalleryTagRepository galleryTagRepository;
    private final TagService tagService;

    @Transactional
    public void updateGallery(Long galleryId, GalleryUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Gallery gallery = galleryService.getGalleryByGalleryId(galleryId);

        if (gallery.getUser() != user) {
            throw new BaseException(GalleryExceptionCode.REQUESTED_PERSON_IS_NOT_AUTHOR);
        }

        gallery.updateGallery(
                request.getTitle(),
                request.getDescription(),
                LocalDateTime.parse(request.getPicturedAt())
        );

        updateGalleryTags(gallery, request.getTag());

        if(request.getImages() != null) {
            Long originGalleryImageCnt = galleryImageRepository.countByGallery(gallery);
            user.updateImageCnt(originGalleryImageCnt, request.getImages().size());
        }

        List<GalleryImage> oldImages = galleryImageRepository.findGalleryImage(galleryId);
        for(GalleryImage galleryImage : oldImages) {
            galleryImage.updatePicturedAt(LocalDateTime.parse(request.getPicturedAt()));
        }

        List<GalleryImage> toDelete = oldImages.stream()
                .filter(img -> !request.getOriginalImages().contains(img.getUrl())).toList();
        galleryImageRepository.deleteAll(toDelete);

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            uploadFileAndCreateGalleryImage(request.getImages(), gallery);
            Sand sand = sandService.getSand(11);
            sandRewardService.addSand(user, sand, sand.getAmount() * request.getImages().size());
            Experience experience = experienceService.getExperience(3);
            experienceRewardService.addExperience(user, experience, experience.getAmount() * request.getImages().size());
        }
    }

    private void uploadFileAndCreateGalleryImage(List<MultipartFile> images, Gallery gallery) {
        List<String> imageUrls;
        try {
            imageUrls = s3Service.uploadFiles(images, Type.CYR);
        } catch (Exception e) {
            throw new BaseException(ImageExceptionCode.FAILED_TO_UPLOAD_IMAGE);
        }

        List<GalleryImage> newImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            GalleryImage galleryImage = GalleryImage.create(gallery, imageUrls.get(i),i+1);
            newImages.add(galleryImage);
        }

        galleryImageRepository.saveAll(newImages);
    }

    private void updateGalleryTags(Gallery gallery, List<String> tagNames) {
        List<GalleryTag> existing = galleryTagService.getGalleryTagByGallery(gallery);

        if (tagNames == null || tagNames.isEmpty()) {
            galleryTagRepository.deleteAll(existing);
            return;
        }

        List<Tag> newTags = tagNames.stream()
                .map(tagService::getOrCreateTag)
                .toList();

        for (GalleryTag gt : existing) {
            if (!newTags.contains(gt.getTag())) {
                galleryTagRepository.delete(gt);
            }
        }

        for (Tag tag : newTags) {
            boolean exists = existing.stream().anyMatch(gt -> gt.getTag().equals(tag));
            if (!exists) {
                galleryTagService.createGalleryTag(gallery, tag);
            }
        }
    }
}
