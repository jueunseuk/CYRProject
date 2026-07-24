package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.GalleryImageRepository;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileRandomImageFlow {

    private final UserService userService;
    private final GalleryImageRepository galleryImageRepository;

    @Transactional
    public String updateUserProfileImage(Integer userId) {
        User user = userService.getUserById(userId);

        List<GalleryImage> galleryImageList = galleryImageRepository.findAll();

        int random = (int) (Math.random() * galleryImageList.size());
        user.updateProfileUrl(galleryImageList.get(random).getUrl());

        return user.getProfileUrl();
    }
}
