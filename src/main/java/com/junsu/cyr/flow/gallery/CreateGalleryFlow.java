package com.junsu.cyr.flow.gallery;

import com.junsu.cyr.domain.experiences.Experience;
import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.gallery.Tag;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.sand.Sand;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.gallery.GalleryUploadRequest;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.GalleryRepository;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.code.ImageExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.experience.ExperienceRewardService;
import com.junsu.cyr.service.experience.ExperienceService;
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
public class CreateGalleryFlow {

    private final UserService userService;
    private final GalleryRepository galleryRepository;
    private final S3Service s3Service;
    private final GalleryImageRepository galleryImageRepository;
    private final SandRewardService sandRewardService;
    private final SandService sandService;
    private final ExperienceService experienceService;
    private final ExperienceRewardService experienceRewardService;
    private final TagService tagService;
    private final GalleryTagService galleryTagService;

    @Transactional
    public void createGallery(GalleryUploadRequest request, Integer userId) {
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

        if(!request.getTag().isEmpty()) {
            for(String name : request.getTag()) {
                Tag tag = tagService.getOrCreateTag(name);
                galleryTagService.createGalleryTag(gallery, tag);
            }
        }

        user.updateImageCnt(0L, request.getImages().size());

        Sand sand = sandService.getSand(11);
        sandRewardService.addSand(user, sand, sand.getAmount() * request.getImages().size());
        Experience experience = experienceService.getExperience(3);
        experienceRewardService.addExperience(user, experience, experience.getAmount() * request.getImages().size());

        uploadFileAndCreateGalleryImage(request.getImages(), gallery);
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
}
