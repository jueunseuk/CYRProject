package com.junsu.cyr.flow.user.profile;

import com.junsu.cyr.domain.images.Image;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.service.image.ImageService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileImageFlow {

    private final UserService userService;
    private final ImageService imageService;

    @Transactional
    public String updateUserProfileImage(MultipartFile request, Integer userId) {
        User user = userService.getUserById(userId);

        if (request != null) {
            Image image = imageService.uploadImage(request, userId.longValue(), Type.PROFILE);
            user.updateProfileUrl(image.getUrl());
        }

        return user.getProfileUrl();
    }
}
