package com.junsu.cyr.service.gallery;

import com.junsu.cyr.domain.gallery.Gallery;
import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.gallery.GalleryTag;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.gallery.*;
import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.repository.GalleryRepository;
import com.junsu.cyr.repository.GalleryTagRepository;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.GalleryExceptionCode;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final UserRepository userRepository;
    private final GalleryTagRepository galleryTagRepository;

    public Gallery getGalleryByGalleryId(Long galleryId) {
        return galleryRepository.findByGalleryId(galleryId)
                .orElseThrow(() -> new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY));
    }

    public Page<GalleryImageResponse> getAllGalleryImages(GalleryImageRequest condition) {
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(condition.getSort()).descending());

        Page<GalleryImage> galleryImages;
        if(condition.getName() == null) {
            galleryImages = galleryImageRepository.findAll(pageable);
        } else {
            galleryImages = galleryImageRepository.findAllByTagName(condition.getName(), pageable);
        }

        return galleryImages.map(GalleryImageResponse::new);
    }

    public GalleryResponse getGallery(Long galleryId) {
        Gallery gallery = galleryRepository.findByGalleryId(galleryId)
                .orElseThrow(() -> new BaseException(GalleryExceptionCode.NO_EXIST_GALLERY));

        List<String> galleryImages = galleryImageRepository.findGalleryImageList(galleryId);

        if(galleryImages.isEmpty()) {
            throw new BaseException(GalleryExceptionCode.NO_EXIST_IMAGE);
        }

        List<GalleryTag> tags = galleryTagRepository.findAllByGallery(gallery);
        List<String> tagResponses = tags.stream().map(galleryTag -> galleryTag.getTag().getName()).toList();

        gallery.updateViewCnt();

        return new GalleryResponse(gallery, galleryImages, tagResponses);
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

    public Long getGalleryImageCnt() {
        return galleryImageRepository.count();
    }

    public Long getGalleryImageCnt(LocalDateTime start, LocalDateTime now) {
        return galleryImageRepository.countByCreatedAtBetween(start, now);
    }

    public Page<Gallery> searchByTitle(SearchConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        return galleryRepository.findAllByTitleContaining(condition.getKeyword(), pageable);
    }

    public List<GalleryImage> getGalleryImageByGallery(Gallery gallery) {
        return galleryImageRepository.findAllByGallery(gallery);
    }
}